package session.statistics.minMaxAvgMedianDifficultyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class SplitOnlyValueDifficultyLevelFromSessionTypeInteger extends DoFn<KV<String,Integer>,Integer> {
    @ProcessElement
    public void apply(ProcessContext c){
        c.output(c.element().getValue());
    }
}
