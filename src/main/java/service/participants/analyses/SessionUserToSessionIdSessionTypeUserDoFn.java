package service.participants.analyses;

import model.operational.db.Session;
import model.operational.db.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionUserToSessionIdSessionTypeUserDoFn extends DoFn<KV<Session, User>, KV<KV<Integer, String>, User>> {
    @ProcessElement
    public void transform(ProcessContext context) {
        int id = Objects.requireNonNull(context.element()).getKey().getSessionId();
        String sessionType = Objects.requireNonNull(context.element()).getKey().getSessionType();
        User user = Objects.requireNonNull(context.element()).getValue();

        context.output(KV.of(KV.of(id, sessionType), user));
    }
}
