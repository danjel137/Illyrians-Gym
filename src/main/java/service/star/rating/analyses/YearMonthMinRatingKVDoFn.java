package service.star.rating.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;
import java.util.stream.StreamSupport;

public class YearMonthMinRatingKVDoFn extends DoFn<KV<String, Iterable<Double>>, KV<String, Double>> {
    @ProcessElement
    public void processElement(ProcessContext context) {

        Double min = StreamSupport.stream(
                        Objects.requireNonNull(context.element())
                                .getValue().spliterator(), false)
                .min(Double::compare).orElseGet(() -> (double) -1);

        if (min != -1) {
            context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), min));
        }

    }
}
