package service.participants.analyses;

import model.operationalDatabase.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class SessionTypeUserGenderDoFn extends DoFn<KV<String, User>, KV<String, String>> {

    String gender = "default";

    public SessionTypeUserGenderDoFn() {
    }

    public SessionTypeUserGenderDoFn(String gender) {
        this.gender = gender;
    }

    @ProcessElement
    public void apply(ProcessContext context) {
        if (gender.equals("default")) {
    context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), Objects.requireNonNull(context.element()).getValue().getGender()));
        }else if (Objects.requireNonNull(context.element()).getValue().getGender().equals(gender)){
            context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), Objects.requireNonNull(context.element()).getValue().getGender()));
        }

    }
}
