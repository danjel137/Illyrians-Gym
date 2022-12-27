import data.dataFromInnerResources.JsonDictionaryWithAvailableIds;
import data.dataFromOperationalDB.GetAllFromUserSessionTable;
import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import model.operationalDatabase.UserSession;
import model.utilities.IdWrapper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import service.starRatingAnalyses.StarRatingStatisticsPerMonthAnalytics;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {

        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

        String sessionStatisticsFilePath = null;
        String participantsStatisticsFilePath = null;
        String starRatingStatisticsFilePath = null;

        //Map<String, Integer> map = JsonDictionaryWithAvailableIds.getJsonObjectWithIdsAsMap();

        IdWrapper availableWrappedIdForRatingStatistics = null;
        IdWrapper availableWrappedIdForParticipantsStatistics = null;
        IdWrapper availableWrappedIdForSessionStatistics = new IdWrapper(1);

        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/config.properties"));

            sessionStatisticsFilePath = properties.get("sessionStatisticsFilePath").toString();
            participantsStatisticsFilePath = properties.get("participantsStatisticsFilePath").toString();
            starRatingStatisticsFilePath = properties.get("starRatingAnalyticsFilePath").toString();

            Map map = JsonDictionaryWithAvailableIds.getJsonObjectWithIdsAsMap();

            availableWrappedIdForRatingStatistics = new IdWrapper(Integer.parseInt(map.get("starRatingAnalyseId").toString()));
            availableWrappedIdForParticipantsStatistics = new IdWrapper(Integer.parseInt(map.get("participantsAnalyseId").toString()));
            availableWrappedIdForSessionStatistics = new IdWrapper(Integer.parseInt(map.get("sessionAnalyseId").toString()));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //Todo build the class that manages extraction only for data that is not processed yet from database

        PCollection<UserSession> data = GetAllFromUserSessionTable.get(pipeline)
                .apply("Display and filter null pointers", ParDo.of(new DoFn<UserSession, UserSession>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        System.out.println(Objects.requireNonNull(context.element()));
                        UserSession elem = context.element();
                        if (elem.getDateRegisteredSession() != null) {
                            context.output(context.element());
                        }

                    }
                }));

        PCollection<StarRatingStatisticsPerMonth> ratingStats = StarRatingStatisticsPerMonthAnalytics
                .calculate(pipeline, data, availableWrappedIdForSessionStatistics);

        ratingStats.apply(ParDo.of(new DoFn<StarRatingStatisticsPerMonth, Void>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                System.out.println(context.element());
            }
        }));


        pipeline.run();
    }
}
