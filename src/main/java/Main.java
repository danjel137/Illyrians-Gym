import com.google.api.services.bigquery.model.TableRow;
import data.dataFromOperationalDB.GetAllFromSessionTable;
import model.analyticsDatabase.SessionStatistics;
import model.operationalDatabase.Session;
//import org.apache.beam.runners.dataflow.DataflowRunner;
//import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.runners.core.construction.renderer.PipelineDotRenderer;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.statistics.MinMaxAvgMedianSessionRepeat.UtilityMinMaxAvgMedianRepeat;
import session.statistics.avgGenderRate.KVUserGender;
import session.statistics.avgGenderRate.UtilsRateGender;
import session.statistics.minMaxAvgMedianDifficultyLevel.UtiliyDifficultyLevel;
import session.statistics.most.time.frequent.gender.UtilityTimeFrequent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;




public class Main {



    public static void main(String[] args) {
//        final DataflowPipelineOptions options = PipelineOptionsFactory.as(DataflowPipelineOptions.class);
//
//        // final Logger LOGGER = LoggerFactory.getLogger(WriteToBq.class);
//        options.setProject("symmetric-hull-368913");
//        options.setRunner(DataflowRunner.class);
//        options.setGcpTempLocation("gs://kotrobt/temp");
//        options.setRegion("europe-west1");
//        options.setJobName("writeSessionStatisticsGymProject");


        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);


        PCollectionView<List<KV<String, Integer>>> maxDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(0).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> maxDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(1).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> maxDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(2).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> maxDiffLevelPersonalTraining= UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(3).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> maxDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(4).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> maxDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(5).apply(View.asList());

        PCollectionView<List<KV<String, Integer>>> minDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(0).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> minDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(1).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> minDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(2).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> minDiffLevelPersonalTraining = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(3).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> minDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(4).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> minDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(5).apply(View.asList());

        PCollectionView<List<KV<String, Integer>>> medianDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(0).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> medianDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(1).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> medianDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(2).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> medianDiffLevelPersonalTraining = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(3).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> medianDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(4).apply(View.asList());
        PCollectionView<List<KV<String, Integer>>> medianDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(5).apply(View.asList());

        PCollectionView<List<KV<String, Double>>> avgDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(0).apply(View.asList());
        PCollectionView<List<KV<String, Double>>> avgDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(1).apply(View.asList());
        PCollectionView<List<KV<String, Double>>> avgDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(2).apply(View.asList());
        PCollectionView<List<KV<String, Double>>> avgDiffLevelPersonalTraining = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(3).apply(View.asList());
        PCollectionView<List<KV<String, Double>>> avgDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(4).apply(View.asList());
        PCollectionView<List<KV<String, Double>>> avgDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(5).apply(View.asList());

        UtilsRateGender.setPipeline(pipeline);
        PCollectionView<List<KV<String, Double>>> mostTimeFrequentFromMale = UtilityTimeFrequent.mostTimeFrequentMale();
        PCollectionView<List<KV<String, Double>>> mostTimeFrequentFromFemale = UtilityTimeFrequent.mostTimeFrequentFemale();

        UtilityMinMaxAvgMedianRepeat utilityMinMaxAvgMedianRepeat =new UtilityMinMaxAvgMedianRepeat(pipeline);
        PCollectionView<List<String>> minRepeatSession = utilityMinMaxAvgMedianRepeat.getMinRepeatSession(pipeline).apply(View.asList());
        PCollectionView<List<String>> maxRepeatSession = utilityMinMaxAvgMedianRepeat.getMaxRepeatSession(pipeline).apply(View.asList());
        PCollectionView<List<Double>> avgRepeatSession = utilityMinMaxAvgMedianRepeat.getAvgRateNumSession(pipeline).apply(View.asList());

        TableRow tbrw=new TableRow();
        pipeline.apply(Create.of(1))
                .apply(ParDo.of(new DoFn<Integer, TableRow>() {
            @ProcessElement
            public void processElement(ProcessContext context) {

                System.out.println( context.sideInput(maxDiffLevelYoga));
                System.out.println( context.sideInput(maxDiffLevelEndurance));
                System.out.println( context.sideInput(maxDiffLevelFartlek));
                System.out.println( context.sideInput(maxDiffLevelPersonalTraining));
                System.out.println( context.sideInput(maxDiffLevelCrossfit));
                System.out.println( context.sideInput(maxDiffLevelBodyBuilding));

                System.out.println( context.sideInput(minDiffLevelYoga));
                System.out.println( context.sideInput(minDiffLevelEndurance));
                System.out.println( context.sideInput(minDiffLevelFartlek));
                System.out.println( context.sideInput(minDiffLevelPersonalTraining));
                System.out.println( context.sideInput(minDiffLevelCrossfit));
                System.out.println( context.sideInput(minDiffLevelBodyBuilding));

                System.out.println( context.sideInput(medianDiffLevelYoga));
                System.out.println( context.sideInput(medianDiffLevelEndurance));
                System.out.println( context.sideInput(medianDiffLevelFartlek));
                System.out.println( context.sideInput(medianDiffLevelPersonalTraining));
                System.out.println( context.sideInput(medianDiffLevelCrossfit));
                System.out.println( context.sideInput(medianDiffLevelBodyBuilding));

                System.out.println( context.sideInput(avgDiffLevelYoga));
                System.out.println( context.sideInput(avgDiffLevelEndurance));
                System.out.println( context.sideInput(avgDiffLevelFartlek));
                System.out.println( context.sideInput(avgDiffLevelPersonalTraining));
                System.out.println( context.sideInput(avgDiffLevelCrossfit));
                System.out.println( context.sideInput(avgDiffLevelBodyBuilding));

                System.out.println(context.sideInput(mostTimeFrequentFromMale));
                System.out.println(context.sideInput(mostTimeFrequentFromFemale));

                System.out.println(context.sideInput(minRepeatSession));
                System.out.println(context.sideInput(maxRepeatSession));
                System.out.println(context.sideInput(avgRepeatSession));

                context.output(new TableRow()
                        .set("maxDiffLevelYoga",maxDiffLevelYoga)
                        .set("maxDiffLevelEndurance",maxDiffLevelEndurance)
                        .set("maxDiffLevelFartlek",maxDiffLevelFartlek)
                        .set("maxDiffLevelPersonalTraining",maxDiffLevelPersonalTraining)
                        .set("maxDiffLevelCrossfit",maxDiffLevelCrossfit)
                        .set("maxDiffLevelBodyBuilding",maxDiffLevelBodyBuilding)
                        .set("minDiffLevelYoga",minDiffLevelYoga)
                        .set("minDiffLevelEndurance",minDiffLevelEndurance)
                        .set("minDiffLevelFartlek",minDiffLevelFartlek)
                        .set("minDiffLevelPersonalTraining",minDiffLevelPersonalTraining)
                        .set("minDiffLevelCrossfit",minDiffLevelCrossfit)
                        .set("minDiffLevelBodyBuilding",minDiffLevelBodyBuilding)
                        .set("medianDiffLevelYoga",medianDiffLevelYoga)
                        .set("medianDiffLevelEndurance",medianDiffLevelEndurance)
                        .set("medianDiffLevelFartlek",medianDiffLevelFartlek)
                        .set("medianDiffLevelPersonalTraining",medianDiffLevelPersonalTraining)
                        .set("medianDiffLevelCrossfit",medianDiffLevelCrossfit)
                        .set("medianDiffLevelBodyBuilding",medianDiffLevelBodyBuilding)
                        .set("avgDiffLevelYoga",avgDiffLevelYoga)
                        .set("avgDiffLevelEndurance",avgDiffLevelEndurance)
                        .set("avgDiffLevelFartlek",avgDiffLevelFartlek)
                        .set("avgDiffLevelPersonalTraining",avgDiffLevelPersonalTraining)
                        .set("avgDiffLevelCrossfit",avgDiffLevelCrossfit)
                        .set("avgDiffLevelBodyBuilding",avgDiffLevelBodyBuilding)
                        .set("mostTimeFrequentFromMale",mostTimeFrequentFromMale)
                        .set("mostTimeFrequentFromFemale",mostTimeFrequentFromFemale)
                        .set("minRepeatSession",minRepeatSession)
                        .set("maxRepeatSession",maxRepeatSession)
                        .set("avgRepeatSession",avgRepeatSession));

            }
        }).withSideInputs(maxDiffLevelYoga,maxDiffLevelEndurance,maxDiffLevelFartlek,maxDiffLevelPersonalTraining,maxDiffLevelCrossfit,maxDiffLevelBodyBuilding,
                minDiffLevelYoga,minDiffLevelEndurance,minDiffLevelFartlek,minDiffLevelPersonalTraining,minDiffLevelCrossfit,minDiffLevelBodyBuilding,
                medianDiffLevelYoga,medianDiffLevelEndurance,medianDiffLevelFartlek,medianDiffLevelPersonalTraining,medianDiffLevelCrossfit,medianDiffLevelBodyBuilding,
                avgDiffLevelYoga,avgDiffLevelEndurance,avgDiffLevelFartlek,avgDiffLevelPersonalTraining,avgDiffLevelCrossfit,avgDiffLevelBodyBuilding,
                mostTimeFrequentFromMale,mostTimeFrequentFromFemale,minRepeatSession,maxRepeatSession,avgRepeatSession
        ));
//                .apply(BigQueryIO.writeTableRows().to("symmetric-hull-368913.session.session_statistics")
//                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
//                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND).withCustomGcsTempLocation(new ValueProvider<String>() {
//                            @Override
//                            public String get() {
//                                return "gs://kotrobt/tempbq";
//                            }
//
//                            @Override
//                            public boolean isAccessible() {
//                                return true;
//                            }
//                        }));


//        UtilsRateGender.setPipeline(pipeline);
//        UtilsRateGender.getBiggestRateGenderAvgOfAllTypeSession();

//        UtilityMinMaxAvgMedianRepeat utilityMinMaxAvgMedianRepeat1=new UtilityMinMaxAvgMedianRepeat(pipeline);
//        utilityMinMaxAvgMedianRepeat1.getAvgRateNumSession(pipeline);

//          UtilityTimeFrequent.mostTimeFrequentMale();

     //   UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(0);

        pipeline.run();

    }

}
