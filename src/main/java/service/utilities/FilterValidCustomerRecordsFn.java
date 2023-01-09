package service.utilities;

import model.operational.db.User;
import org.apache.beam.sdk.transforms.DoFn;

public class FilterValidCustomerRecordsFn extends DoFn<User, User> {
    @ProcessElement
    public void filter(ProcessContext context) {
        User user = context.element();

        if (user != null
                && user.getUserType() != null
                && user.getFirstName() != null
                && user.getLastName() != null
                && user.getEmail() != null
                && user.getBirthday() != null
                && user.getPhoneNumber() != null
                && user.getPassword() != null
                && user.getTimeDateRegistered() != null
                && user.getEndTimeSubscription() != null
                && user.getGender() != null
        ) {
            context.output(user);
        }

    }
}
