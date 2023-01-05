package service.participants.analyses;

import model.operationalDatabase.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class ExtractSessionTypeNameSurnameDoFn extends DoFn<KV<KV<String, User>, Long>, KV<String, String>>{
    @ProcessElement
    public void process(ProcessContext context) {

        String sessionType = Objects.requireNonNull(context.element()).getKey().getKey();
        String nameSurname = Objects.requireNonNull(context.element()).getKey().getValue().getFirstName() +
                "," +
                Objects.requireNonNull(context.element()).getKey().getValue().getLastName();

        context.output(KV.of(sessionType, nameSurname));
    }
}
