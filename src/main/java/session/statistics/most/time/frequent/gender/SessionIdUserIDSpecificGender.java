package session.statistics.most.time.frequent.gender;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class SessionIdUserIDSpecificGender extends DoFn<KV<String, Integer>,KV<String,String>> {
    @ProcessElement
    public void aVoid(ProcessContext c){
        c.output(KV.of(String.valueOf(c.element().getValue()),c.element().getKey()));
    }
}
