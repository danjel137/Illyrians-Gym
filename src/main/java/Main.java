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
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) {

        final DataflowPipelineOptions options = PipelineOptionsFactory.as(DataflowPipelineOptions.class);
        options.setProject("illyrians-gym-project");
        options.setGcpTempLocation("gs://gcf-v2-sources-832801291670-europe-central2/temp");
        options.setRegion("europe-west2");
        options.setJobName("GymProjectPipelines");

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


        PCollectionView<List<KV<String, Double>>> yearMonthMinView = yearMonthMin.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> yearMonthMaxView = yearMonthMax.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> yearMonthMedianView = yearMonthMedian.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> yearMonthAverageView = yearMonthAverage.apply(View.asList());

        PCollectionView<List<UserSession>> userSessionsView = userSessions.apply(View.asList());

        PCollection<StarRatingStatisticsPerMonth> ratingStats = GenerateRatingStatistics.getTransform(pipeline
                , yearMonthMinView
                , yearMonthMaxView
                , yearMonthMedianView
                , yearMonthAverageView
                , availableMappedIdsView);

        // calculating participants statistics

        PCollection<KV<Session, User>> sessionUser = SessionToSessionUserKVTransform
                .getTransform(sessions, customersView, userSessionsView);

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

        PCollection<KV<KV<String, User>, Long>> sessionTypeUserAttendanceCount = sessionTypeUser
                .apply(Count.perElement());

        PCollection<KV<KV<String, User>, Long>> sessionTypeMostFrequentUserCount = sessionTypeUserAttendanceCount
                .apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeNameSurnameOfMostFrequentUser = sessionTypeMostFrequentUserCount
                .apply(ParDo.of(new ExtractSessionTypeNameSurnameDoFn()));

        // calculating the most frequent male per session, name, surname

        PCollection<KV<String, User>> sessionTypeMaleUser = sessionTypeUser
                .apply(ParDo.of(new FilterSessionTypeUserByGender(GenderType.M.toString())));

        PCollection<KV<KV<String, User>, Long>> sessionTypeMaleUserCount = sessionTypeMaleUser.apply(Count.perElement());

        PCollection<KV<KV<String, User>, Long>> sessionTypeMostFrequentMaleUserCount = sessionTypeMaleUserCount.apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeNameSurnameOfMostFrequentMaleUser = sessionTypeMostFrequentMaleUserCount
                .apply(ParDo.of(new ExtractSessionTypeNameSurnameDoFn()));

        //calculating the most frequent female per session, name surname

        PCollection<KV<String, User>> sessionTypeFemaleUser = sessionTypeUser
                .apply(ParDo.of(new FilterSessionTypeUserByGender(GenderType.F.toString())));

        PCollection<KV<KV<String, User>, Long>> sessionTypeFemaleUserCount = sessionTypeFemaleUser
                .apply(Count.perElement());

        PCollection<KV<KV<String, User>, Long>> sessionTypeMostFrequentFemaleUserCount = sessionTypeFemaleUserCount
                .apply(Max.perKey());

        PCollection<KV<String, String>> sessionTypeNameSurnameOfMostFrequentFemaleUser = sessionTypeMostFrequentFemaleUserCount
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

        PCollection<ParticipantsStatistics> participantStats = GenerateParticipantStatistics.getTransform(pipeline
                , sessionTypeMinView
                , sessionTypeMaxView
                , sessionTypeAverageView
                , sessionTypeMedianView
                , sessionTypeMostFrequentGenderView
                , sessionTypeNameSurnameOfMostFrequentUserView
                , sessionTypeNameSurnameOfMostFrequentMaleUserView
                , sessionTypeNameSurnameOfMostFrequentFemaleUserView
                , availableMappedIdsView);

        Properties properties = new Properties();

        String ratingStatsFilePath;
        String ratingStatsFileHeader = HeadersOfCsvFiles.STAR_RATING_STATISTICS;

        String participantStatsFilePath;
        String participantStatsFileHeader = HeadersOfCsvFiles.PARTICIPANT_STATISTICS_HEADERS;

        try {

            Reader reader = new FileReader("src/main/resources/config.properties");
            properties.load(reader);

            ratingStatsFilePath = properties.getProperty("starRatingAnalyticsFilePath");
            participantStatsFilePath = properties.getProperty("participantsStatisticsFilePath");

            participantStats.apply(WriteToParticipantsStatisticsAnalyseFile
                    .getTransform(participantStatsFilePath, participantStatsFileHeader));

            ratingStats.apply(WriteToStarRatingAnalyseFile
                    .getTransform(ratingStatsFilePath, ratingStatsFileHeader));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        pipeline.run().waitUntilFinish();

        JsonDictionaryWithAvailableIds.updateAll(idAvailableValues);
    }
}
