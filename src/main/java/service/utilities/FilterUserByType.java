package service.utilities;

import model.operational.db.User;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Objects;

public class FilterUserByType extends DoFn<User, User> {
    String type;

    public FilterUserByType(String type) {
        this.type = type;
    }

    @ProcessElement
    public void filter(ProcessContext context) {
        if (context.element() != null) {
            if (Objects.requireNonNull(context.element()).getUserType() != null) {
                if (Objects.requireNonNull(context.element()).getUserType().equals(type)) {
                    context.output(context.element());
                }
            }
        }
    }

}
