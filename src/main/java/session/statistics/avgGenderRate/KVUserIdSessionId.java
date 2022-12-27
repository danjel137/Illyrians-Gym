package session.statistics.avgGenderRate;

import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class KVUserIdSessionId extends DoFn<UserSession, KV<String, Integer>> {
  @ProcessElement
  public void apply(ProcessContext c) {
    UserSession userSession = c.element();
    // System.out.println(userSession.getUserId()+" "+userSession.getSessionId());
    c.output(KV.of(String.valueOf(userSession.getUserId()), userSession.getSessionId()));
  }
}
