package session.statistics.MinMaxAvgMedianSession;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public class UtilityMinMaxAvgMedian implements Serializable {
    final private Pipeline pipeline;
    public String MinRepeatSession;
    public String MaxRepeatSession;
    public String AvgRateNumSession;
    public String MedianNumSession;

    public UtilityMinMaxAvgMedian(Pipeline pipeline) {
        this.pipeline = pipeline;
//        MinRepeatSession = String.valueOf(getMinRepeatSession());
//        MaxRepeatSession=String.valueOf(getMaxRepeatSession());
//        AvgRateNumSession=String.valueOf(getAvgRateNumSession());
//        MedianNumSession=String.valueOf(getMedianNumSession());
    }


    public PCollection<KV<String, Integer>> getNumPerSessionRepeat(Pipeline p) {
        return p.apply(GetAllFromSessionTable.get())

                .apply("KV Session , 1", ParDo.of(new SessionType()))
                .apply("count  session is repeated", GroupByKey.create())
                .apply(ParDo.of(new NumSession()));
    }

    public PCollection<String> getMinRepeatSession(Pipeline p) {
        return getNumPerSessionRepeat(p).apply("min session repeat", ParDo.of(new MinSessionRepeat()));
    }

    public PCollection<String> getMaxRepeatSession(Pipeline p) {
        return getNumPerSessionRepeat(p).apply("max session repeat", ParDo.of(new MaxSessionRepeat()));
    }

    public PCollection<Double> getAvgRateNumSession(Pipeline p) {
        return getNumPerSessionRepeat(p).apply("Extract num sesion", ParDo.of(new ExtractOnlyNumSession()))
                .apply("", Mean.globally());
    }

    public PCollection<Double> getMedianNumSession(Pipeline p) {
        return getNumPerSessionRepeat(p).apply(ParDo.of(new DoFn<KV<String, Integer>, Double>() {
            @ProcessElement
            public void apply(ProcessContext c) {
                c.output(Double.valueOf(c.element().getValue()));
            }
        })).apply(Combine.globally(new Median()));
    }
}
