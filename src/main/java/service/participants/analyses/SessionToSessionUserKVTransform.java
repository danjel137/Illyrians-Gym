package service.participants.analyses;

import model.operational.db.Session;
import model.operational.db.User;
import model.operational.db.UserSession;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import java.util.List;
import java.util.Objects;

public class SessionToSessionUserKVTransform {

    private SessionToSessionUserKVTransform() {
    }

    public static PCollection<KV<Session, User>> getTransform(PCollection<Session> sessions
            , PCollectionView<List<User>> customersView
            , PCollectionView<List<UserSession>> userSessionsView) {

        return sessions.apply("session type, gender KV", ParDo.of(new DoFn<Session, KV<Session, User>>() {
            @ProcessElement
            public void processElement(ProcessContext context) {

                List<UserSession> userSessionsList = context.sideInput(userSessionsView);
                List<User> usersList = context.sideInput(customersView);

                for (UserSession userSession : userSessionsList) {
                    if (Objects.requireNonNull(context.element()).getSessionId() == userSession.getSessionId()) {

                        for (User user : usersList) {
                            if (user.getUserId() == userSession.getUserId()) {
                                context.output(KV.of(Objects.requireNonNull(context.element()), user));
                            }
                        }
                    }
                }
            }
        }).withSideInputs(customersView, userSessionsView));

    }
}
