package session.statistics.most.time.frequent.gender;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class NumSessionId extends DoFn<KV<String, Iterable<String>>, KV<String, Double>> {
    @ProcessElement
    public void aVoid(ProcessContext c) {
        String strKey = c.element().getKey();
        Iterable<String> strValue = c.element().getValue();
        Integer sum = 0;
        for (String loop : strValue) {
            sum++;
        }
        //System.out.println(KV.of(strKey, sum));
        c.output(KV.of(strKey, Double.valueOf(sum)));
    }
}
