package session.statistics.MinMaxAvgMedianSession;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtractOnlyNumSession extends DoFn<KV<String,Integer>,Double> {
    @ProcessElement
    public void apply(ProcessContext c){
        c.output(Double.valueOf(c.element().getValue()));
       // System.out.println(c.element().getValue());
    }

}
