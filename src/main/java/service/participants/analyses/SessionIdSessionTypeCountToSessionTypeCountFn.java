package service.participants.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionIdSessionTypeCountToSessionTypeCountFn extends DoFn<KV<KV<Integer, String>, Long>, KV<String, Long>> {

    @ProcessElement
    public void transform(ProcessContext context) {
        String sessionType = Objects.requireNonNull(context.element()).getKey().getValue();
        Long count = context.element().getValue();

        context.output(KV.of(sessionType,count));
    }
}
