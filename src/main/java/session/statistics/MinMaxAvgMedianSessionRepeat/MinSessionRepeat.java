package session.statistics.MinMaxAvgMedianSessionRepeat;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class MinSessionRepeat extends DoFn<KV<String,Integer>,String> {
    @ProcessElement
    public void filter(ProcessContext c) {
        if (c.element().getValue() == NumSession.min) {
           // System.out.println(c.element());
            c.output(c.element().getKey()+": "+c.element().getValue());
        }
    }
}
