package service.utilities;

import model.analyticsDatabase.ParticipantsStatistics;
import model.analyticsDatabase.SessionStatistics;
import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import model.utilities.IdWrapper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
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

    public static PCollection<StarRatingStatisticsPerMonth> applyForRating(Pipeline pipeline, PCollection<StarRatingStatisticsPerMonth> stats, IdWrapper availableIdInAWrapper) {
        PCollectionView<IdWrapper> availableIdInAWrapperView = pipeline.apply(Create.of(availableIdInAWrapper).withCoder(SerializableCoder.of(IdWrapper.class))).apply(View.asSingleton());
        return stats.apply(ParDo.of(new DoFn<StarRatingStatisticsPerMonth, StarRatingStatisticsPerMonth>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                IdWrapper validId = context.sideInput(availableIdInAWrapperView);
                Objects.requireNonNull(context.element()).setId(validId.getUniqueId().getAndIncrement());
                context.output(context.element());

            }
        }).withSideInputs(availableIdInAWrapperView));
    }

    public static PCollection<ParticipantsStatistics> applyForParticipants(Pipeline pipeline, PCollection<ParticipantsStatistics> stats,
                                                                           IdWrapper availableIdInAWrapper) {
        PCollectionView<IdWrapper> availableIdInAWrapperView = pipeline.apply(Create.of(availableIdInAWrapper).withCoder(SerializableCoder.of(IdWrapper.class))).apply(View.asSingleton());
        return stats.apply(ParDo.of(new DoFn<ParticipantsStatistics, ParticipantsStatistics>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                IdWrapper validId = context.sideInput(availableIdInAWrapperView);
                Objects.requireNonNull(context.element()).setId(validId.getUniqueId().getAndIncrement());
                context.output(context.element());
            }
        }).withSideInputs(availableIdInAWrapperView));
    }

    public static PCollection<SessionStatistics> applyForSession(Pipeline pipeline, PCollection<SessionStatistics> stats, IdWrapper availableIdInAWrapper) {
        PCollectionView<IdWrapper> availableIdInAWrapperView = pipeline.apply(Create.of(availableIdInAWrapper).withCoder(SerializableCoder.of(IdWrapper.class))).apply(View.asSingleton());
        return stats.apply(ParDo.of(new DoFn<SessionStatistics, SessionStatistics>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                IdWrapper validId = context.sideInput(availableIdInAWrapperView);
                Objects.requireNonNull(context.element()).setId(validId.getUniqueId().getAndIncrement());
                context.output(context.element());
            }
        }).withSideInputs(availableIdInAWrapperView));
    }

}