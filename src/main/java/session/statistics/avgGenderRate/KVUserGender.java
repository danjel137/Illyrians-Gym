package session.statistics.avgGenderRate;

import model.operationalDatabase.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import static session.statistics.most.time.frequent.gender.UtilityTimeFrequent.gender;
public class KVUserGender extends DoFn<User, KV<String, String>> {


    @ProcessElement
    public void apply(ProcessContext c) {
        User user = c.element();
        if (user.getGender().equals(gender) && user.getUserType().equals("CUSTOMER")) {
            //System.out.println(user.getUserId()+":"+user.getGender());
            c.output(KV.of(String.valueOf(user.getUserId()), user.getGender()));
        }
    }
}
