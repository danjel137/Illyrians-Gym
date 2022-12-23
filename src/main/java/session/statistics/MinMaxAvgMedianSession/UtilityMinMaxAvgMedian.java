package session.statistics.MinMaxAvgMedianSession;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public class UtilityMinMaxAvgMedian implements Serializable {
    Pipeline pipeline;

    public  String MinRepeatSession= String.valueOf(getMinRepeatSession());
    public String MaxRepeatSession;
    public String AvgRateNumSession;
    public String MedianNumSession;

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public PCollection<KV<String, Integer>> getNumPerSessionRepeat(){
         return        GetAllFromSessionTable.get(pipeline)

                        .apply("KV Session , 1", ParDo.of(new SessionType()))
                        .apply("count  session is repeated", GroupByKey.create())
                        .apply(ParDo.of(new NumSession()));
    }

    public PCollection <String> getMinRepeatSession(){
      return   getNumPerSessionRepeat().apply("min session repeat",ParDo.of(new MinSessionRepeat()));
    }

    public PCollection <String>getMaxRepeatSession (){
        return   getNumPerSessionRepeat().apply("max session repeat",ParDo.of(new MaxSessionRepeat()));
    }

    public PCollection <Double>getAvgRateNumSession (){
      return   getNumPerSessionRepeat().apply("Extract num sesion",ParDo.of(new ExtractOnlyNumSession()))
                .apply("", Mean.globally());
    }

    public PCollection<Double> getMedianNumSession(){
       return getNumPerSessionRepeat().apply(ParDo.of(new DoFn<KV<String, Integer>, Double>() {
            @ProcessElement
            public void apply (ProcessContext c){
                c.output(Double.valueOf(c.element().getValue()));
            }
        })).apply(Combine.globally(new Median()));
    }
}
