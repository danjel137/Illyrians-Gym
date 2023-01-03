package session.statistics.minMaxAvgMedianDifficultyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianDifficultyLevel extends DoFn<KV<String, Iterable<Integer>>, KV<String, Integer>> {
    @ProcessElement
    public void processElement(
            @Element KV<String, Iterable<Integer>> element,
            OutputReceiver<KV<String, Integer>> out) {
        String sessionType = element.getKey();
        List<Integer> list = new ArrayList<>();
        for (Integer iterate : element.getValue()) {
            list.add(iterate);
        }
        int median;
        Collections.sort(list);
        if (list.size() % 2 == 1) {

            median = list.get((list.size() + 1) / 2 - 1);
        } else {
            median = (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2;

        }
        out.output(KV.of(sessionType,median));

    }
}
