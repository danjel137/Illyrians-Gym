package service.star.rating.analyses;

import model.operational.db.UserSession;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class UserSessionToYearMonthRatingKVFn extends DoFn<UserSession, KV<String, Double>> {
    @ProcessElement
    public void processElement(ProcessContext context) {
        if (context.element() != null  && Objects.requireNonNull(context.element()).getDateRegisteredSession() != null) {
            String date = Objects.requireNonNull(context.element())
                    .getDateRegisteredSession()
                    .split("-")[0] +
                    "-" +
                    Objects.requireNonNull(context.element())
                            .getDateRegisteredSession()
                            .split("-")[1];

            context.output(KV.of(date,
                    Objects.requireNonNull(context.element()).getRate()));
        }
    }
}
