package service;

import model.analyticsDatabase.SessionStatisticsPerMonth;
import model.operationalDatabase.Session;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.util.Objects;

public class SessionStatisticsPerMonthAnalytics {

    private SessionStatisticsPerMonthAnalytics() {
    }

    public static PCollection<SessionStatisticsPerMonth> calculate(PCollection<Session> input, Pipeline pipeline) {

        PCollection<KV<String, Session>> groupedDataByDate =
                input.apply("Create key-value pairs of date and session",
                        ParDo.of(new DoFn<Session, KV<String, Session>>() {
                            @ProcessElement
                            public void processElement(ProcessContext context) {
                                context.output(KV.of(Objects.requireNonNull(context.element()).getTimeDate()
                                        .split(" ")[0], context.element()));
                            }
                        }));

        PCollection<KV<String, Session>> groupedDataByMonthOfYear = input.apply("Create key value pairs of month of year and session", ParDo.of(new DoFn<Session, KV<String, Session>>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                context.output(KV.of(GetFromValidTimeStamp.yearMonth(context.element().getTimeDate()), context.element()));
            }
        }));

        PCollection<KV<String, Iterable<Session>>> monthOfYearListOfSessionsKV = groupedDataByMonthOfYear.apply("Grouping key values of day of month and list of sessions", GroupByKey.create());

        monthOfYearListOfSessionsKV.apply("Creating key values of month of year and session count",
                ParDo.of(new DoFn<KV<String, Iterable<Session>>, KV<String, Long>>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        long count = 0;
                        for (Session element : Objects.requireNonNull(context.element()).getValue()) {
                            count++;
                        }
                        context.output(KV.of(context.element().getKey(), count));
                    }
                })
        );


        PCollection<KV<String, Long>> numSessionsPerDay = groupedDataByDate.apply("Find count of sessions per day",
                Count.perKey());

        PCollection<Long> count = numSessionsPerDay.apply("Extract count",
                ParDo.of(new DoFn<KV<String, Long>, Long>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        context.output(context.element().getValue());
                    }
                }));

        PCollection<Long> minSessionCountADay = count.apply("Calculate min",
                Min.longsGlobally());

        PCollection<Long> maxSessionCountADay = count.apply("Calculate Max",
                Max.longsGlobally());

        PCollection<Double> meanSessionCountADay = count.apply("Calculate mean",
                Mean.globally());


        PCollection<Long> averageSessionCountADay = pipeline.apply(
                Create.of(
                        Long.parseLong(count.apply(Sum.longsGlobally()).toString())
                                / Integer.parseInt(count.toString())));


        return pipeline.apply(Create.of(new SessionStatisticsPerMonth()))
                .apply(ParDo.of(new DoFn<SessionStatisticsPerMonth, SessionStatisticsPerMonth>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        SessionStatisticsPerMonth elem = context.element();
                        elem.setId();
                        elem.setMonth(GetFromValidTimeStamp.month());
                        elem.setYear(GetFromValidTimeStamp.year());
                        elem.setMinCountDay(Short.parseShort(minSessionCountADay.toString()));
                        elem.setMaxCountPerDay(Short.parseShort(maxSessionCountADay.toString()));
                        elem.setMeanCountPerDay(Float.parseFloat(meanSessionCountADay.toString()));
                        elem.setAverageCountPerDay(Float.parseFloat(averageSessionCountADay.toString()));
                    }
                }));
    }
}
