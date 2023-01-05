package service.participants.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionTypeSessionIdCountToSessionTypeCount extends DoFn<KV<String, KV<Integer, Long>>, KV<String, Long>> {
    @ProcessElement
    public void transform(ProcessContext context) {
        String sessionType = Objects.requireNonNull(context.element()).getKey();
        Long count = Objects.requireNonNull(context.element()).getValue().getValue();

        context.output(KV.of(sessionType, count));
    }
}
