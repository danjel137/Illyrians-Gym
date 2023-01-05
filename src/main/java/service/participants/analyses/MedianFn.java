package service.participants.analyses;

import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;
import java.util.*;

public class MedianFn extends Combine.CombineFn<KV<String, Long>, MedianFn.Accumulator, Map<String, Double>> {

    static class Accumulator implements Serializable {
        Map<String, List<Long>> sessionTypeCountMap = new HashMap<>();
        Map<String, Integer> counterMap = new HashMap<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Accumulator that = (Accumulator) o;
            return Objects.equals(sessionTypeCountMap, that.sessionTypeCountMap) && Objects.equals(counterMap, that.counterMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sessionTypeCountMap, counterMap);
        }
    }

    @Override
    public Accumulator createAccumulator() {
        return new Accumulator();
    }

    @Override
    public Accumulator addInput(Accumulator accumulator, KV<String, Long> input) {

        if (accumulator != null && input != null) {

            if (accumulator.sessionTypeCountMap.containsKey(input.getKey())) {

                List<Long> values = accumulator.sessionTypeCountMap.get(input.getKey());
                values.add(input.getValue());
                accumulator.sessionTypeCountMap.put(input.getKey(), values);

                Integer counterForKey = accumulator.counterMap.get(input.getKey());
                accumulator.counterMap.put(input.getKey(), counterForKey + 1);

            } else {
                List<Long> values = new ArrayList<>();
                values.add(input.getValue());
                accumulator.sessionTypeCountMap.put(input.getKey(), values);
                accumulator.counterMap.put(input.getKey(), 0);
            }
        }
        return accumulator;
    }

    @Override
    public Accumulator mergeAccumulators(Iterable<Accumulator> accumulators) {

        Accumulator merged = createAccumulator();

        for (Accumulator accumulator : accumulators) {
            merged.sessionTypeCountMap.putAll(accumulator.sessionTypeCountMap);
        }

        return merged;
    }

    @Override
    public Map<String, Double> extractOutput(Accumulator accumulator) {

        Map<String, Double> result = new HashMap<>();

        assert accumulator != null;
        Set<Map.Entry<String, List<Long>>> valuesPerKey = accumulator.sessionTypeCountMap.entrySet();

        for (Map.Entry<String, List<Long>> entry : valuesPerKey) {

            List<Long> valuesList = entry.getValue();
            Collections.sort(valuesList);
            if (valuesList.size() % 2 == 0) {
                result.put(entry.getKey(), (valuesList.get(valuesList.size() / 2 - 1) + (double) valuesList.get(valuesList.size() / 2) / 2));
            } else {
                result.put(entry.getKey(), Double.valueOf(valuesList.get(valuesList.size() / 2)));
            }

        }
        return result;

    }
}


