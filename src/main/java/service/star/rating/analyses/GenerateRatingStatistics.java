package service.star.rating.analyses;

import model.analytics.db.StarRatingStatisticsPerMonth;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GenerateRatingStatistics {

    private GenerateRatingStatistics() {
    }

    public static PCollection<StarRatingStatisticsPerMonth> getTransform(Pipeline pipeline
            , PCollectionView<List<KV<String, Double>>> yearMonthMinView
            , PCollectionView<List<KV<String, Double>>> yearMonthMaxView
            , PCollectionView<List<KV<String, Double>>> yearMonthMedianView
            , PCollectionView<List<KV<String, Double>>> yearMonthAverageView
            , PCollectionView<Map<String, AtomicInteger>> availableMappedIdsView) {

        return pipeline.apply(Create.of(0))
                .apply(ParDo.of(new DoFn<Integer, StarRatingStatisticsPerMonth>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {

                        List<KV<String, Double>> timeMin = context.sideInput(yearMonthMinView);
                        List<KV<String, Double>> timeMax = context.sideInput(yearMonthMaxView);
                        List<KV<String, Double>> timeMedian = context.sideInput(yearMonthMedianView);
                        List<KV<String, Double>> timeAverage = context.sideInput(yearMonthAverageView);
                        Map<String, AtomicInteger> availableIds = context.sideInput(availableMappedIdsView);
                        List<String> months = timeMin.stream().map(KV::getKey).collect(Collectors.toList());

                        for (String month : months) {

                            StarRatingStatisticsPerMonth stats = new StarRatingStatisticsPerMonth();
                            stats.setId(availableIds.get("starRatingAnalyseId").getAndIncrement());

                            stats.setYear(Integer.parseInt(month.split("-")[0]));
                            stats.setMonth(Integer.parseInt(month.split("-")[1]));

                            for (KV<String, Double> monthMinKVRecord : timeMin) {
                                if (month.equals(monthMinKVRecord.getKey())) {
                                    stats.setMinStar(monthMinKVRecord.getValue());
                                }
                            }

                            for (KV<String, Double> monthMaxKVRecord : timeMax) {
                                if (month.equals(monthMaxKVRecord.getKey())) {
                                    stats.setMaxStar(monthMaxKVRecord.getValue());
                                }
                            }

                            for (KV<String, Double> monthMedianKVRecord : timeMedian) {
                                if (month.equals(monthMedianKVRecord.getKey())) {
                                    stats.setMedianStar(monthMedianKVRecord.getValue());
                                }
                            }

                            for (KV<String, Double> monthAverageKVRecord : timeAverage) {
                                if (month.equals(monthAverageKVRecord.getKey())) {
                                    stats.setMedianStar(monthAverageKVRecord.getValue());
                                }
                            }
                            context.output(stats);
                        }
                    }
                }).withSideInputs(yearMonthMinView, yearMonthMaxView, yearMonthMedianView, yearMonthAverageView, availableMappedIdsView));
    }
}