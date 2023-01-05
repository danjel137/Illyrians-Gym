package service.participants.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class ExtractSessionTypeMostFrequentGenderDoFn extends DoFn<KV<KV<String, String>, Long>, KV<String, String>> {

    @ProcessElement
    public void extract(ProcessContext context) {
        context.output(KV.of(Objects.requireNonNull(context.element()).getKey().getKey(),
                Objects.requireNonNull(context.element()).getKey().getValue()));
    }
}
