package service.starRatingAnalyses;

import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import model.operationalDatabase.UserSession;
import model.utilities.IdWrapper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StarRatingStatisticsPerMonthAnalytics {
    //TODO refactor using Atomic Transforms
    private StarRatingStatisticsPerMonthAnalytics() {
    }

    public static PCollection<StarRatingStatisticsPerMonth> calculate(Pipeline pipeline
            , PCollection<UserSession> input, IdWrapper availableIdInAWrapper) {

        PCollectionView<IdWrapper> availableIdInAWrapperView =
                pipeline.apply(Create.of(availableIdInAWrapper)
                                .withCoder(SerializableCoder.of(IdWrapper.class)))
                        .apply(View.asSingleton());

        PCollection<KV<String, Double>> dateRateKv = input.apply(
                "UserSession to kv(year-month, rating)", ParDo.of(new DoFn<UserSession, KV<String, Double>>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {

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
                }));

        PCollection<KV<String, Iterable<Double>>> ratesOfAMonthYear =
                dateRateKv.apply("Grouping by time dimension", GroupByKey.create());

        PCollection<KV<String, Double>> minRatePerMonth =
                ratesOfAMonthYear.apply(ParDo.of(new DoFn<KV<String, Iterable<Double>>, KV<String, Double>>() {

                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        Double min = StreamSupport.stream(
                                        Objects.requireNonNull(context.element())
                                                .getValue().spliterator(), false)
                                .min(Double::compare).orElseGet(() -> (double) -1);

                        if (min != -1) {
                            context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), min));
                        }
                    }
                }));

        PCollection<KV<String, Double>> maxRatePerMonth =
                ratesOfAMonthYear.apply(ParDo.of(new DoFn<KV<String, Iterable<Double>>, KV<String, Double>>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {

                        Double max = StreamSupport.stream(
                                        Objects.requireNonNull(context.element())
                                                .getValue().spliterator(), false)
                                .max(Double::compare).orElseGet(() -> (double) -1);

                        if (max != -1) {
                            context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), max));
                        }
                    }
                }));

        PCollection<KV<String, Double>> medianRatePerMonth =
                ratesOfAMonthYear.apply(ParDo.of(new DoFn<KV<String, Iterable<Double>>, KV<String, Double>>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {

                        List<Double> listOfElements = StreamSupport.stream(
                                Objects.requireNonNull(context.element())
                                        .getValue().spliterator(), false).sorted(Double::compare).collect(Collectors.toList());

                        double median;
                        if (listOfElements.size() % 2 == 0) {
                            median = (listOfElements.get(listOfElements.size() / 2) + listOfElements.get((listOfElements.size() / 2) + 1)) / 2;
                        } else {
                            median = listOfElements.get(listOfElements.size() / 2);
                        }
                        context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), median));
                    }
                }));

        PCollection<KV<String, Double>> averageRatePerMonth =
                ratesOfAMonthYear.apply(ParDo.of(new DoFn<KV<String, Iterable<Double>>, KV<String, Double>>() {
                                                     @ProcessElement
                                                     public void processElement(ProcessContext context) {
                                                         Double sum = StreamSupport.stream(
                                                                         Objects.requireNonNull(context.element())
                                                                                 .getValue().spliterator(), false)
                                                                 .filter(element -> element > 0)
                                                                 .reduce((double) 0, Double::sum);

                                                         long count = StreamSupport.stream(
                                                                         Objects.requireNonNull(context.element())
                                                                                 .getValue().spliterator(), false)
                                                                 .filter(element -> element > 0)
                                                                 .count();

                                                         System.out.println("count: " + count);
                                                         System.out.println("sum: " + sum);
                                                         System.out.println("sum / count : " + (sum / count));

                                                         context.output(KV.of(Objects.requireNonNull(context.element()).getKey(), (sum / count)));
                                                     }
                                                 }
                ));

        PCollectionView<List<KV<String, Double>>> minRatePerMonthList = minRatePerMonth.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> maxRatePerMonthList = maxRatePerMonth.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> medianRatePerMonthList = medianRatePerMonth.apply(View.asList());
        PCollectionView<List<KV<String, Double>>> averageRatePerMonthList = averageRatePerMonth.apply(View.asList());

        return pipeline.apply(Create.of(1)).apply(ParDo.of(new DoFn<Integer, StarRatingStatisticsPerMonth>() {
            @ProcessElement
            public void processElement(ProcessContext context) {

                List<KV<String, Double>> monthMinKV = context.sideInput(minRatePerMonthList);
                List<KV<String, Double>> monthMaxKV = context.sideInput(maxRatePerMonthList);
                List<KV<String, Double>> monthMedianKV = context.sideInput(medianRatePerMonthList);
                List<KV<String, Double>> monthAverageKV = context.sideInput(averageRatePerMonthList);
                IdWrapper id = context.sideInput(availableIdInAWrapperView);

                List<String> months = monthMinKV.stream().map(KV::getKey).collect(Collectors.toList());

                for (String month : months) {

                    StarRatingStatisticsPerMonth stats = new StarRatingStatisticsPerMonth();
                    stats.setId(id.getUniqueId().getAndIncrement());

                    stats.setYear(Integer.parseInt(month.split("-")[0]));
                    stats.setMonth(Integer.parseInt(month.split("-")[1]));

                    for (KV<String, Double> monthMinKVRecord : monthMinKV) {
                        if (month.equals(monthMinKVRecord.getKey())) {
                            stats.setMinStar(monthMinKVRecord.getValue());
                            break;
                        }
                    }

                    for (KV<String, Double> monthMaxKVRecord : monthMaxKV) {
                        if (month.equals(monthMaxKVRecord.getKey())) {
                            stats.setMaxStar(monthMaxKVRecord.getValue());
                            break;
                        }
                    }

                    for (KV<String, Double> monthMedianKVRecord : monthMedianKV) {
                        if (month.equals(monthMedianKVRecord.getKey())) {
                            stats.setMedianStar(monthMedianKVRecord.getValue());
                            break;
                        }
                    }

                    for (KV<String, Double> monthAverageKVRecord : monthAverageKV) {
                        if (month.equals(monthAverageKVRecord.getKey())) {
                            stats.setAverageStar(monthAverageKVRecord.getValue());
                            break;
                        }
                    }
                    context.output(stats);
                }
            }
        }).withSideInputs(minRatePerMonthList,
                maxRatePerMonthList,
                medianRatePerMonthList,
                averageRatePerMonthList,
                availableIdInAWrapperView));
    }
}
