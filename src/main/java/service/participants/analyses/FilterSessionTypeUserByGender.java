package service.participants.analyses;

import model.operational.db.User;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class FilterSessionTypeUserByGender extends DoFn<KV<String, User>, KV<String, User>> {

    String gender;

    public FilterSessionTypeUserByGender(String gender) {
        this.gender = gender;
    }

    @ProcessElement
    public void filter(ProcessContext context) {
        if (Objects.requireNonNull(context.element()).getValue().getGender().equals(gender)) {
            context.output(context.element());
        }
    }
}
