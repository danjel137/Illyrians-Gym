package service.star.rating.analyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;
import java.util.stream.StreamSupport;

public class YearMonthAverageRatingKVDoFn extends DoFn<KV<String, Iterable<Double>>, KV<String, Double>> {
    @ProcessElement
    public void processElement(ProcessContext context) {
        Double sum = StreamSupport.stream(
                        Objects.requireNonNull(context.element())
                                .getValue().spliterator(), false)
                .filter(element -> element > 0)
                .reduce((double) 0, Double::sum);

        long count = StreamSupport.stream(
                        Objects.requireNonNull(context.element())
                                .getValue().spliterator(), false)
                .filter(element -> element > 0)
                .count();

        context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), (sum / count)));
    }
}
