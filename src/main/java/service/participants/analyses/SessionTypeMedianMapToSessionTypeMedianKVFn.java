package service.participants.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Map;
import java.util.Objects;

public class SessionTypeMedianMapToSessionTypeMedianKVFn extends DoFn<Map<String, Double>, KV<String, Double>> {
    @ProcessElement
    public void transform(ProcessContext context) {
        for (Map.Entry<String, Double> entry : Objects.requireNonNull(context.element()).entrySet()) {
            context.output(KV.of(entry.getKey(), entry.getValue()));
        }
    }
}
