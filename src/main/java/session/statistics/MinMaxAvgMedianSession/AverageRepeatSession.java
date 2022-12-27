package session.statistics.MinMaxAvgMedianSession;


import org.apache.beam.sdk.transforms.SerializableFunction;

public class AverageRepeatSession implements SerializableFunction<Iterable<Integer>, Integer> {
  @Override
  public Integer apply(Iterable<Integer> input) {
    //System.out.println(input);
    int sum = 0;
    int count = 0;
    for (int item : input) {
      sum += item;
      count += 1;
    }

    return sum / count;
  }
}
