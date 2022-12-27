package service.starRatingAnalyses;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MonthYearMedianRatingKVDoFn extends DoFn<KV<String, Iterable<Double>>, KV<String, Double>> {
    @ProcessElement
    public void processElement(ProcessContext context) {
        List<Double> listOfElements = StreamSupport.stream(
                Objects.requireNonNull(context.element())
                        .getValue().spliterator(), false).sorted(Double::compare).collect(Collectors.toList());

        double median;
        if (listOfElements.size() % 2 == 0) {
            median = (listOfElements.get(listOfElements.size() / 2) + listOfElements.get((listOfElements.size() / 2) + 1)) / 2;
        } else {
            median = listOfElements.get(listOfElements.size() / 2);
        }
        context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), median));
    }
}
