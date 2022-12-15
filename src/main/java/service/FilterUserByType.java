package service;

import model.operationalDatabase.User;
import model.operationalDatabase.UserType;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.util.Objects;

public class FilterUserByType {
    private FilterUserByType() {
    }

    public static PCollection<User> get(PCollection<User> allUsers, UserType type) {
       return allUsers.apply(ParDo.of(new DoFn<User, User>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                if (Objects.requireNonNull(context.element()).getUserType().equals(type.toString())) {
                    context.output(context.element());
                }
            }
        }));
    }
}
