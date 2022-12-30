package session.statistics.most.time.frequent.gender;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import javax.print.DocFlavor;

public class ExtractSessionIdStartedTime extends DoFn<Session, KV<String, String>> {
    @ProcessElement
    public void aVoid(ProcessContext c) {
        Session session = c.element();
        //System.out.println(KV.of(session.getStartTime(), String.valueOf(session.getSessionId())));
        c.output(KV.of(String.valueOf(session.getSessionId()), session.getStartTime()));

    }
}
