package session.statistics.avgGenderRate;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class ExtractRateName extends DoFn<KV<String, KV<Integer, String>>, KV<String, Integer>> {
  @ProcessElement
  public void apply(ProcessContext c) {
    c.output(KV.of(c.element().getValue().getValue(), c.element().getValue().getKey()));
  }

}
