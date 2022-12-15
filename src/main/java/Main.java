import data.dataFromOperationalDB.GetAllFromSessionTable;
import data.dataFromOperationalDB.GetAllFromUserSessionTable;
import model.operationalDatabase.Session;
import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import java.util.Objects;


public class Main {

    public static void main(String[] args) {

        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

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
