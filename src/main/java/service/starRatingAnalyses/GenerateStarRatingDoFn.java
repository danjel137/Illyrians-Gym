package service.starRatingAnalyses;

import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PBegin;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateStarRatingDoFn extends DoFn<PBegin, StarRatingStatisticsPerMonth> {

    List<KV<String, Double>> monthYearMinRatingKV;
    List<KV<String, Double>> monthYearMaxRatingKV;
    List<KV<String, Double>> monthYearMedianRatingKV;
    List<KV<String, Double>> monthYearAverageRatingKV;

    public GenerateStarRatingDoFn(List<KV<String, Double>> monthYearMinRatingKV
            , List<KV<String, Double>> monthYearMaxRatingKV,
                                  List<KV<String, Double>> monthYearMedianRatingKV,
                                  List<KV<String, Double>> monthYearAverageRatingKV
    ) {
        this.monthYearMinRatingKV = monthYearMinRatingKV;
        this.monthYearMaxRatingKV = monthYearMaxRatingKV;
        this.monthYearMedianRatingKV = monthYearMedianRatingKV;
        this.monthYearAverageRatingKV = monthYearAverageRatingKV;
    }

    @ProcessElement
    public void processElement(ProcessContext context) {

        List<String> months = monthYearMinRatingKV.stream().map(KV::getKey).collect(Collectors.toList());

        for (String month : months) {

            StarRatingStatisticsPerMonth stats = new StarRatingStatisticsPerMonth();
            stats.setYear(Integer.parseInt(month.split("-")[0]));
            stats.setMonth(Integer.parseInt(month.split("-")[1]));

            for (KV<String, Double> monthMinKVRecord : monthYearMinRatingKV) {
                if (month.equals(monthMinKVRecord.getKey())) {
                    stats.setMinStar(monthMinKVRecord.getValue());
                }
            }

            for (KV<String, Double> monthMaxKVRecord : monthYearMaxRatingKV) {
                if (month.equals(monthMaxKVRecord.getKey())) {
                    stats.setMaxStar(monthMaxKVRecord.getValue());
                }
            }

            for (KV<String, Double> monthMedianKVRecord : monthYearMedianRatingKV) {
                if (month.equals(monthMedianKVRecord.getKey())) {
                    stats.setMedianStar(monthMedianKVRecord.getValue());
                }
            }

            for (KV<String, Double> monthAverageKVRecord : monthYearAverageRatingKV) {
                if (month.equals(monthAverageKVRecord.getKey())) {
                    stats.setMedianStar(monthAverageKVRecord.getValue());
                }
            }
            context.output(stats);
        }
    }
}
