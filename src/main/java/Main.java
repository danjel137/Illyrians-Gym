import data.inner.resources.JsonDictionaryWithAvailableIds;
import data.operational.GetAllFromSessionTable;
import data.operational.GetAllFromUserSessionTable;
import data.operational.GetAllFromUserTable;
import data.to.csv.files.WriteToParticipantsStatisticsAnalyseFile;
import data.to.csv.files.WriteToStarRatingAnalyseFile;
import model.analytics.db.HeadersOfCsvFiles;
import model.analytics.db.ParticipantsStatistics;
import model.analytics.db.StarRatingStatisticsPerMonth;
import model.operational.db.*;
import model.utilities.UserCoder;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import service.participants.analyses.*;
import service.star.rating.analyses.*;
import service.utilities.FilterUserByType;
import service.utilities.FilterValidCustomerRecordsFn;
import service.utilities.FilterValidSessionRecordsFn;

import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        PipelineOptions options = PipelineOptionsFactory.create();

        Pipeline pipeline = Pipeline.create(options);

        //Data insertion into application

        PCollection<Session> sessions = pipeline.apply(GetAllFromSessionTable.getTransform())
                .apply(ParDo.of(new FilterValidSessionRecordsFn()));

        PCollection<User> users = pipeline.apply(GetAllFromUserTable.getTransform())
                .setCoder(UserCoder.of());

        PCollection<User> customers = users.apply(ParDo.of(new FilterUserByType(UserType.CUSTOMER.toString())))
                .apply(ParDo.of(new FilterValidCustomerRecordsFn()));

        PCollection<UserSession> userSessions = pipeline.apply(GetAllFromUserSessionTable.getTransform());

        //reading  available ids from json and creating the view
        Map<String, AtomicInteger> idAvailableValues = JsonDictionaryWithAvailableIds.getJsonObjectWithIdsAsMap();

        PCollectionView<Map<String, AtomicInteger>> availableMappedIdsView = pipeline
                .apply(Create.of(idAvailableValues)).apply(View.asMap());

        PCollectionView<List<User>> customersView = customers.apply(View.asList());

        PCollection<KV<String, Iterable<Double>>> ratingsPerYearMonth = userSessions
                .apply(ParDo.of(new UserSessionToYearMonthRatingKVFn()))
                .apply(GroupByKey.create());

        PCollection<KV<String, Double>> yearMonthMin = ratingsPerYearMonth
                .apply(ParDo.of(new YearMonthMinRatingKVDoFn()));

        PCollection<KV<String, Double>> yearMonthMax = ratingsPerYearMonth
                .apply(ParDo.of(new YearMonthMaxRatingKVDoFn()));

        PCollection<KV<String, Double>> yearMonthMedian = ratingsPerYearMonth
                .apply(ParDo.of(new YearMonthMedianRatingKVDoFn()));

        PCollection<KV<String, Double>> yearMonthAverage = ratingsPerYearMonth
                .apply(ParDo.of(new YearMonthAverageRatingKVDoFn()));

        // creating views

        PCollectionView<List<KV<String, Double>>> yearMonthMinView = yearMonthMin.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> yearMonthMaxView = yearMonthMax.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> yearMonthMedianView = yearMonthMedian.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> yearMonthAverageView = yearMonthAverage.apply(View.asList());

        PCollectionView<List<UserSession>> userSessionsView = userSessions.apply(View.asList());

        // calculating rating statistics

        PCollection<StarRatingStatisticsPerMonth> ratingStats = pipeline.apply(Create.of(1))
                .apply(ParDo.of(new DoFn<Integer, StarRatingStatisticsPerMonth>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {

                        List<KV<String, Double>> timeMin = context.sideInput(yearMonthMinView);
                        List<KV<String, Double>> timeMax = context.sideInput(yearMonthMaxView);
                        List<KV<String, Double>> timeMedian = context.sideInput(yearMonthMedianView);
                        List<KV<String, Double>> timeAverage = context.sideInput(yearMonthAverageView);
                        Map<String, AtomicInteger> availableIds = context.sideInput(availableMappedIdsView);
                        List<String> months = timeMin.stream().map(KV::getKey).collect(Collectors.toList());

                        for (String month : months) {

                            StarRatingStatisticsPerMonth stats = new StarRatingStatisticsPerMonth();
                            stats.setId(availableIds.get("starRatingAnalyseId").getAndIncrement());

                            stats.setYear(Integer.parseInt(month.split("-")[0]));
                            stats.setMonth(Integer.parseInt(month.split("-")[1]));

                            for (KV<String, Double> monthMinKVRecord : timeMin) {
                                if (month.equals(monthMinKVRecord.getKey())) {
                                    stats.setMinStar(monthMinKVRecord.getValue());
                                }
                            }

                            for (KV<String, Double> monthMaxKVRecord : timeMax) {
                                if (month.equals(monthMaxKVRecord.getKey())) {
                                    stats.setMaxStar(monthMaxKVRecord.getValue());
                                }
                            }

                            for (KV<String, Double> monthMedianKVRecord : timeMedian) {
                                if (month.equals(monthMedianKVRecord.getKey())) {
                                    stats.setMedianStar(monthMedianKVRecord.getValue());
                                }
                            }

                            for (KV<String, Double> monthAverageKVRecord : timeAverage) {
                                if (month.equals(monthAverageKVRecord.getKey())) {
                                    stats.setMedianStar(monthAverageKVRecord.getValue());
                                }
                            }
                            context.output(stats);
                        }
                    }
                }).withSideInputs(yearMonthMinView, yearMonthMaxView, yearMonthMedianView, yearMonthAverageView, availableMappedIdsView));

// todo delete after test this anonymous displaying
        ratingStats.apply(ParDo.of(new DoFn<StarRatingStatisticsPerMonth, Void>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                System.out.println(context.element());
            }

        }));

        // calculating participants statistics

        PCollection<KV<Session, User>> sessionUser = sessions.apply("session type, gender KV", ParDo.of(new DoFn<Session, KV<Session, User>>() {
            @ProcessElement
            public void processElement(ProcessContext context) {

                List<UserSession> userSessionsList = context.sideInput(userSessionsView);
                List<User> usersList = context.sideInput(customersView);

                for (UserSession userSession : userSessionsList) {
                    if (Objects.requireNonNull(context.element()).getSessionId() == userSession.getSessionId()) {

                        for (User user : usersList) {
                            if (user.getUserId() == userSession.getUserId()) {
                                context.output(KV.of(Objects.requireNonNull(context.element()), user));
                            }
                        }
                    }
                }
            }
        }).withSideInputs(customersView, userSessionsView));

        PCollection<KV<String, User>> sessionTypeUser = sessionUser
                .apply(ParDo.of(new SessionUserToSessionTypeUserDoFn()))
                .setCoder(KvCoder.of(StringUtf8Coder.of(), UserCoder.of()));

        //calculating min , max, median, average of participant statistics

        PCollection<KV<KV<Integer, String>, User>> sessionIdSessionTypeUser = sessionUser
                .apply(ParDo.of(new SessionUserToSessionIdSessionTypeUserDoFn()));

        PCollection<KV<KV<Integer, String>, Long>> sessionIdSessionTypeCount = sessionIdSessionTypeUser
                .apply(Count.perKey());

        PCollection<KV<String, KV<Integer, Long>>> sessionTypeSessionIdCount = sessionIdSessionTypeCount
                .apply(ParDo.of(new SessionTypeSessionIdCountWithTypeAsKeyDoFn()));

        PCollection<KV<String, Long>> sessionTypeCount = sessionIdSessionTypeCount
                .apply(ParDo.of(new SessionIdSessionTypeCountToSessionTypeCountFn()));

        PCollection<KV<String, Long>> sessionTypeMin = sessionTypeSessionIdCount
                .apply(Min.perKey((a, b) -> (int) (a.getValue() - b.getValue())))
                .apply(ParDo.of(new SessionTypeSessionIdCountToSessionTypeCount()));

        PCollection<KV<String, Long>> sessionTypeMax = sessionTypeSessionIdCount
                .apply(Max.perKey((a, b) -> (int) (a.getValue() - b.getValue())))
                .apply(ParDo.of(new SessionTypeSessionIdCountToSessionTypeCount()));

        PCollection<KV<String, Double>> sessionTypeAverage = sessionTypeCount
                .apply(Mean.perKey());

        PCollection<KV<String, Double>> sessionTypeMedian = sessionTypeCount.apply(Combine.globally(new MedianFn()))
                .apply(ParDo.of(new SessionTypeMedianMapToSessionTypeMedianKVFn()));

        // calculating most frequent gender per session

        PCollection<KV<String, String>> sessionTypeGender = sessionTypeUser
                .apply(ParDo.of(new SessionTypeUserGenderDoFn()));

        PCollection<KV<KV<String, String>, Long>> sessionTypeGenderCount = sessionTypeGender
                .apply(Count.perElement());

        PCollection<KV<KV<String, String>, Long>> sessionTypeGenderMax = sessionTypeGenderCount
                .apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeMostFrequentGender = sessionTypeGenderMax
                .apply(ParDo.of(new ExtractSessionTypeMostFrequentGenderDoFn()));

        // calculating most frequent person per session name surname

        PCollection<KV<KV<String, KV<Integer, User>>, Long>> sessionTypeUserAttendanceCount = sessionTypeUser
                .apply(ParDo.of(new DoFn<KV<String, User>, KV<String, KV<Integer, User>>>() {
                    @ProcessElement
                    public void process(ProcessContext context) {
                        if (context.element() != null) {
                            context.output(KV.of(
                                    Objects.requireNonNull(context.element()).getKey(),
                                    KV.of(Objects.requireNonNull(context.element()).getValue().getUserId(),
                                            Objects.requireNonNull(context.element()).getValue())));
                        }
                    }
                }))
                .apply(Count.perElement());

        PCollection<KV<KV<String, KV<Integer, User>>, Long>> sessionTypeMostFrequentUserCount = sessionTypeUserAttendanceCount
                .apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeNameSurnameOfMostFrequentUser = sessionTypeMostFrequentUserCount
                .apply(ParDo.of(new ExtractSessionTypeNameSurnameDoFn()));

        // calculating the most frequent male per session, name, surname

        PCollection<KV<String, User>> sessionTypeMaleUser = sessionTypeUser
                .apply(ParDo.of(new FilterSessionTypeUserByGender(GenderType.M.toString())));

        PCollection<KV<KV<String, User>, Long>> sessionTypeMaleUserCount = sessionTypeMaleUser.apply(Count.perElement());

        PCollection<KV<KV<String, User>, Long>> sessionTypeMostFrequentMaleUserCount = sessionTypeMaleUserCount.apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeNameSurnameOfMostFrequentMaleUser = sessionTypeMostFrequentMaleUserCount
                .apply(ParDo.of(new SessionTypeUserCountToSessionTypeIdUserCount()))
                .apply(ParDo.of(new ExtractSessionTypeNameSurnameDoFn()));

        //calculating the most frequent female per session, name surname

        PCollection<KV<String, User>> sessionTypeFemaleUser = sessionTypeUser
                .apply(ParDo.of(new FilterSessionTypeUserByGender(GenderType.F.toString())));

        PCollection<KV<KV<String, User>, Long>> sessionTypeFemaleUserCount = sessionTypeFemaleUser
                .apply(Count.perElement());

        PCollection<KV<KV<String, User>, Long>> sessionTypeMostFrequentFemaleUserCount = sessionTypeFemaleUserCount
                .apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeNameSurnameOfMostFrequentFemaleUser = sessionTypeMostFrequentFemaleUserCount
                .apply(ParDo.of(new SessionTypeUserCountToSessionTypeIdUserCount()))
                .apply(ParDo.of(new ExtractSessionTypeNameSurnameDoFn()));

        //generate participants statistics

        PCollectionView<List<KV<String, Long>>> sessionTypeMinView = sessionTypeMin.apply(View.asList());

        PCollectionView<List<KV<String, Long>>> sessionTypeMaxView = sessionTypeMax.apply(View.asList());

        PCollectionView<List<KV<String, Double>>> sessionTypeAverageView = sessionTypeAverage.apply(View.asList());

        PCollectionView<List<KV<String, Double>>> sessionTypeMedianView = sessionTypeMedian.apply(View.asList());

        PCollectionView<List<KV<String, String>>> sessionTypeMostFrequentGenderView = sessionTypeMostFrequentGender
                .apply(View.asList());

        PCollectionView<List<KV<String, String>>> sessionTypeNameSurnameOfMostFrequentUserView = sessionTypeNameSurnameOfMostFrequentUser
                .apply(View.asList());

        PCollectionView<List<KV<String, String>>> sessionTypeNameSurnameOfMostFrequentMaleUserView = sessionTypeNameSurnameOfMostFrequentMaleUser
                .apply(View.asList());

        PCollectionView<List<KV<String, String>>> sessionTypeNameSurnameOfMostFrequentFemaleUserView = sessionTypeNameSurnameOfMostFrequentFemaleUser
                .apply(View.asList());

        PCollection<KV<Integer, ParticipantsStatistics>> idParticipantStats = pipeline.apply(Create.of(1))
                .apply(ParDo.of(new DoFn<Integer, KV<Integer, ParticipantsStatistics>>() {
                    final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    @ProcessElement
                    public void processElement(ProcessContext context) {

                        List<KV<String, Long>> sessionTypeMinList = context.sideInput(sessionTypeMinView);
                        List<KV<String, Long>> sessionTypeMaxList = context.sideInput(sessionTypeMaxView);
                        List<KV<String, Double>> sessionTypeAverageList = context.sideInput(sessionTypeAverageView);
                        List<KV<String, Double>> sessionTypeMedianList = context.sideInput(sessionTypeMedianView);
                        List<KV<String, String>> sessionTypeMostFrequentGenderList = context.sideInput(sessionTypeMostFrequentGenderView);
                        List<KV<String, String>> sessionTypeNameSurnameOfMostFrequentUserList = context.sideInput(sessionTypeNameSurnameOfMostFrequentUserView);
                        List<KV<String, String>> sessionTypeNameSurnameOfMostFrequentMaleUserList = context.sideInput(sessionTypeNameSurnameOfMostFrequentMaleUserView);
                        List<KV<String, String>> sessionTypeNameSurnameOfMostFrequentFemaleUserList = context.sideInput(sessionTypeNameSurnameOfMostFrequentFemaleUserView);
                        Map<String, AtomicInteger> availableMappedIds = context.sideInput(availableMappedIdsView);

                        for (KV<String, Long> sessionTypeMinKVPivot : sessionTypeMinList) {

                            ParticipantsStatistics participantsStatistics = new ParticipantsStatistics();
                            Date date = new Date();

                            participantsStatistics.setSessionType(sessionTypeMinKVPivot.getKey());
                            participantsStatistics.setMinNumParticipants(sessionTypeMinKVPivot.getValue());
                            participantsStatistics.setResultDay(formatter.format(date));
                            participantsStatistics.setId(availableMappedIds.get("participantsAnalyseId").getAndIncrement());

                            for (KV<String, Long> sessionTypeMax : sessionTypeMaxList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeMax.getKey())) {
                                    participantsStatistics.setMaxNumParticipants(sessionTypeMax.getValue());
                                }
                            }

                            for (KV<String, Double> sessionTypeAverage : sessionTypeAverageList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeAverage.getKey())) {
                                    participantsStatistics.setAverageNumParticipants(sessionTypeAverage.getValue());
                                }
                            }

                            for (KV<String, Double> sessionTypeMedian : sessionTypeMedianList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeMedian.getKey())) {
                                    participantsStatistics.setMedianNumParticipants(sessionTypeMedian.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeMostFrequentGender : sessionTypeMostFrequentGenderList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeMostFrequentGender.getKey())) {
                                    participantsStatistics.setMostFrequentGender(sessionTypeMostFrequentGender.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeNameSurnameOfMostFrequentUser : sessionTypeNameSurnameOfMostFrequentUserList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeNameSurnameOfMostFrequentUser.getKey())) {
                                    participantsStatistics.setMostFrequentPersonNameSurname(sessionTypeNameSurnameOfMostFrequentUser.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeNameSurnameOfMostFrequentMaleUser : sessionTypeNameSurnameOfMostFrequentMaleUserList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeNameSurnameOfMostFrequentMaleUser.getKey())) {
                                    participantsStatistics.setMostFrequentMaleNameSurname(sessionTypeNameSurnameOfMostFrequentMaleUser.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeNameSurnameOfMostFrequentFemaleUser : sessionTypeNameSurnameOfMostFrequentFemaleUserList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeNameSurnameOfMostFrequentFemaleUser.getKey())) {
                                    participantsStatistics.setMostFrequentMaleNameSurname(sessionTypeNameSurnameOfMostFrequentFemaleUser.getValue());
                                }
                            }
                            context.output(KV.of(participantsStatistics.getId(), participantsStatistics));
                        }

                    }

                }).withSideInputs(sessionTypeMinView,
                        sessionTypeMaxView,
                        sessionTypeAverageView,
                        sessionTypeMedianView,
                        sessionTypeMostFrequentGenderView,
                        sessionTypeNameSurnameOfMostFrequentUserView,
                        sessionTypeNameSurnameOfMostFrequentMaleUserView,
                        sessionTypeNameSurnameOfMostFrequentFemaleUserView,
                        availableMappedIdsView
                ));

        //writing data into buckets

        Properties properties = new Properties();

        String ratingStatsFileName;
        String ratingStatsFileHeader = HeadersOfCsvFiles.STAR_RATING_STATISTICS;

        String participantStatsFileName;
        String participantStatsFileHeader = HeadersOfCsvFiles.PARTICIPANT_STATISTICS_HEADERS;

// todo write valid values in properties file

        try {

            Reader reader = new FileReader("src/main/resources/config.properties");
            properties.load(reader);

            ratingStatsFileName = properties.getProperty("starRatingAnalyticsFilePath");
            participantStatsFileName = properties.getProperty("participantsStatisticsFilePath");

            idParticipantStats.apply(ParDo.of(new ExtractParticipantStatsFn()))
                    .apply(WriteToParticipantsStatisticsAnalyseFile
                            .getTransform(participantStatsFileName, participantStatsFileHeader));

            ratingStats.apply(WriteToStarRatingAnalyseFile
                    .getTransform(ratingStatsFileName, ratingStatsFileHeader));

        } catch (Exception exception) {
            exception.printStackTrace();
        }


        pipeline.run().waitUntilFinish();

        JsonDictionaryWithAvailableIds.updateAll(idAvailableValues);
    }
}
