package session.statistics.minMaxAvgMedianDifficultyLevel;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class KVSessionTypeDifficultyLevel extends DoFn<Session, KV<String,Integer>> {
    @ProcessElement
    public void apply(ProcessContext c){
        Session session=c.element();
        c.output(KV.of(session.getSessionType(),session.getDifficultyLevel()));
    }
}
