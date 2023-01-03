package session.statistics.minMaxAvgMedianDifficultyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class SplitOnlyValueDifficultyLevelFromSessionTypeDouble extends DoFn<KV<String,Double>,Double> {
    public void apply(ProcessContext c){
        c.output(c.element().getValue());
    }
}
