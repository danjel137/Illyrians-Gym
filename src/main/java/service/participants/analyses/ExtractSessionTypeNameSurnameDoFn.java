package service.participants.analyses;

import model.operational.db.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class ExtractSessionTypeNameSurnameDoFn extends DoFn<KV<KV<String, KV<Integer, User>>, Long>, KV<String, String>> {
    @ProcessElement
    public void process(ProcessContext context) {

        String sessionType = Objects.requireNonNull(context.element()).getKey().getKey();

        User user = Objects.requireNonNull(context.element()).getKey().getValue().getValue();
        String nameSurname = user.getFirstName() +
                "," +
                user.getLastName();

        context.output(KV.of(sessionType, nameSurname));
    }
}
