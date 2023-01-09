package service.utilities;

import model.operational.db.Session;
import org.apache.beam.sdk.transforms.DoFn;

public class FilterValidSessionRecordsFn extends DoFn<Session, Session> {
    @ProcessElement
    public void filter(ProcessContext context) {

        Session session = context.element();

        if (session != null
                && session.getSessionType() != null
                && session.getDayWeek() != null
                && session.getDescription() != null
                && session.getStartTime() != null
        ) {
            context.output(session);
        }

    }
}
