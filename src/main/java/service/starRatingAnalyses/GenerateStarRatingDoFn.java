package service.starRatingAnalyses;

import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import model.utilities.IdWrapper;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PBegin;

import java.util.List;

public class GenerateStarRatingDoFn extends DoFn<PBegin, StarRatingStatisticsPerMonth> {
    @ProcessElement
    public void processElement(ProcessContext context) {

//        List<KV<String, Double>> monthMinKV = context.sideInput(minRatePerMonthList);
//        List<KV<String, Double>> monthMaxKV = context.sideInput(maxRatePerMonthList);
//        List<KV<String, Double>> monthMedianKV = context.sideInput(medianRatePerMonthList);
//        List<KV<String, Double>> monthAverageKV = context.sideInput(averageRatePerMonthList);
//        IdWrapper id = context.sideInput(availableIdInAWrapperView);
    }
}
