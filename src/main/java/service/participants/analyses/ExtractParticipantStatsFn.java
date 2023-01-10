package service.participants.analyses;

import model.analytics.db.ParticipantsStatistics;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

public class ExtractParticipantStatsFn extends DoFn<KV<Integer, ParticipantsStatistics>, ParticipantsStatistics> {
    @ProcessElement
    public void extract(ProcessContext context) {
        if (context.element() != null && Objects.requireNonNull(context.element()).getValue() != null) {
            context.output(Objects.requireNonNull(context.element()).getValue());
        }
    }
}
