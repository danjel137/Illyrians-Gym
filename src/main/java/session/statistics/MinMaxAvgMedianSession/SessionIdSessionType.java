package session.statistics.MinMaxAvgMedianSession;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class SessionIdSessionType extends DoFn<Session, KV<String,String>> {
    @ProcessElement
    public void apply(ProcessContext c){
        Session session=c.element();
        //System.out.println(String.valueOf(session.getSessionId())+" "+session.getSessionType());
        c.output(KV.of(String.valueOf(session.getSessionId()),session.getSessionType()));
    }
}
