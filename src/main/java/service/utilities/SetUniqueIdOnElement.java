package service.utilities;

import model.analyticsDatabase.ParticipantsStatistics;
import model.analyticsDatabase.SessionStatistics;
import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import java.util.Objects;

public class SetUniqueIdOnElement {

    private SetUniqueIdOnElement() {
    }

    public PCollection<Object> apply(Pipeline pipeline, PCollection<Object> stats, IdWrapper availableIdInAWrapper) {

        PCollectionView<IdWrapper> availableIdInAWrapperView = pipeline.apply(Create.of(availableIdInAWrapper)).apply(View.asSingleton());

        return stats.apply(ParDo.of(new DoFn<Object, Object>() {
            @ProcessElement
            public void processElement(ProcessContext context) {

                IdWrapper validId = context.sideInput(availableIdInAWrapperView);
                Object element = context.element();

                if (element instanceof SessionStatistics) {
                    SessionStatistics elementAfterCast = (SessionStatistics) element;
                    Objects.requireNonNull(elementAfterCast).setId(validId.getUniqueId().getAndIncrement());
                    context.output(elementAfterCast);

                } else if (element instanceof ParticipantsStatistics) {
                    ParticipantsStatistics elementAfterCast = (ParticipantsStatistics) element;
                    Objects.requireNonNull(elementAfterCast).setId(validId.getUniqueId().getAndIncrement());
                    context.output(elementAfterCast);

                } else {
                    StarRatingStatisticsPerMonth elementAfterCast = (StarRatingStatisticsPerMonth) element;
                    Objects.requireNonNull(elementAfterCast).setId(validId.getUniqueId().getAndIncrement());
                    context.output(elementAfterCast);
                }
            }
        }).withSideInputs(availableIdInAWrapperView));
    }
}