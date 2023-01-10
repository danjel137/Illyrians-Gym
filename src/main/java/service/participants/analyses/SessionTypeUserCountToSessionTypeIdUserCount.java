package service.participants.analyses;

import model.operational.db.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionTypeUserCountToSessionTypeIdUserCount extends DoFn<KV<KV<String, User>, Long>, KV<KV<String, KV<Integer, User>>, Long>> {
    @ProcessElement
    public void process(ProcessContext context) {
        String sessionType = context.element().getKey().getKey();
        User user = Objects.requireNonNull(context.element()).getKey().getValue();
        KV<Integer, User> idUser = KV.of(user.getUserId(), user);
        Long count = context.element().getValue();
        context.output(KV.of(KV.of(sessionType,idUser),count));
    }
}
