import data.dataFromOperationalDB.GetAllFromUserSessionTable;
import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;


public class Main {

    public static void main(String[] args) {

        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

        String sessionStatisticsFilePath = null;
        String participantsStatisticsFilePath = null;
        String starRatingStatisticsFilePath = null;

        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/config.properties"));

            sessionStatisticsFilePath = properties.get("sessionStatisticsFilePath").toString();
            participantsStatisticsFilePath = properties.get("participantsStatisticsFilePath").toString();
            starRatingStatisticsFilePath = properties.get("starRatingAnalyticsFilePath").toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        //Todo build the class that manages extraction only for data that is not processed yet from database

        GetAllFromUserSessionTable.get(pipeline)
                .apply(ParDo.of(new DoFn<UserSession, Void>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        System.out.println(Objects.requireNonNull(context.element()).toString());
                    }

                }));

        pipeline.run();
    }
}
