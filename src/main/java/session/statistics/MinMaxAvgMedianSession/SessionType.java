package session.statistics.MinMaxAvgMedianSession;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class SessionType extends DoFn<Session,KV<String,Integer>> {


    @ProcessElement
    public void apply(ProcessContext c){
        Integer num=1;
        Session session=c.element();
        c.output(KV.of(session.getSessionType(),num));

    }
}
