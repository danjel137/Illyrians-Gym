package service.participants.analyses;

import model.operationalDatabase.Session;
import model.operationalDatabase.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionUserToSessionTypeUserDoFn extends DoFn<KV<Session, User>, KV<String, User>> {
    @ProcessElement
    public void transform(ProcessContext context) {
        context.output(KV.of(Objects.requireNonNull(context.element()).getKey().getSessionType(),
                Objects.requireNonNull(context.element()).getValue()));
    }
}
