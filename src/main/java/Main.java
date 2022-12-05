import data.GetAllFromCustomerSessionTable;
import model.CustomerSession;
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

        GetAllFromCustomerSessionTable.get(pipeline)
                        .apply(ParDo.of(new DoFn<CustomerSession, Void>() {
                            @ProcessElement
                            public void processElement(ProcessContext context) {
                                System.out.println(Objects.requireNonNull(context.element()).toString());
                            }

                        }));
        pipeline.run();
    }
}
