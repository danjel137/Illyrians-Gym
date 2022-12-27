package session.statistics.MinMaxAvgMedianSession;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public class UtilityMinMaxAvgMedian implements Serializable {


  public PCollection<KV<String, Integer>> getNumPerSessionRepeat(Pipeline pipeline) {
    return pipeline.apply(GetAllFromSessionTable.getTransform())

        .apply("KV Session , 1", ParDo.of(new SessionType()))
        .apply("count  session is repeated", GroupByKey.create())
        .apply(ParDo.of(new NumSession()));
  }

  public PCollection<String> getMinRepeatSession(Pipeline pipeline) {
    return getNumPerSessionRepeat(pipeline).apply("min session repeat", ParDo.of(new MinSessionRepeat()));
  }

  public PCollection<String> getMaxRepeatSession(Pipeline pipeline) {
    return getNumPerSessionRepeat(pipeline).apply("max session repeat", ParDo.of(new MaxSessionRepeat()));
  }

  public PCollection<Double> getAvgRateNumSession(Pipeline pipeline) {
    return getNumPerSessionRepeat(pipeline).apply("Extract num sesion", ParDo.of(new ExtractOnlyNumSession()))
        .apply("", Mean.globally());
  }

  public PCollection<Double> getMedianNumSession(Pipeline pipeline) {
    return getNumPerSessionRepeat(pipeline).apply(ParDo.of(new DoFn<KV<String, Integer>, Double>() {
      @ProcessElement
      public void apply(ProcessContext c) {
        c.output(Double.valueOf(c.element().getValue()));
      }
    })).apply(Combine.globally(new Median()));
  }
}
