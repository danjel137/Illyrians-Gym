package service.participants.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionTypeSessionIdCountWithTypeAsKeyDoFn extends DoFn<KV<KV<Integer, String>, Long>, KV<String, KV<Integer, Long>>> {
    @ProcessElement
    public void transform(ProcessContext context) {
        String sessionType = Objects.requireNonNull(context.element()).getKey().getValue();
        Integer id = Objects.requireNonNull(context.element()).getKey().getKey();
        Long count = Objects.requireNonNull(context.element()).getValue();

        context.output(KV.of(sessionType, KV.of(id, count)));
    }
}
