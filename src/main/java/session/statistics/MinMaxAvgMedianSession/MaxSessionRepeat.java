package session.statistics.MinMaxAvgMedianSession;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class MaxSessionRepeat extends DoFn<KV<String,Integer>,String> {
    @ProcessElement
    public void filter(ProcessContext c) {
        if (c.element().getValue() == NumSession.max) {
            //System.out.println(c.element());
            c.output(c.element().getKey()+": "+c.element().getValue());
        }
    }
}
