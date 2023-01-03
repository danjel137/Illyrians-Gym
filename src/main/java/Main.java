import com.google.api.services.bigquery.model.TableRow;
import data.dataFromOperationalDB.GetAllFromSessionTable;
import model.analyticsDatabase.SessionStatistics;
import model.operationalDatabase.Session;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
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

import java.util.ArrayList;
import java.util.List;




public class Main {

//    final DataflowPipelineOptions options = PipelineOptionsFactory.as(DataflowPipelineOptions.class);
//   // final Logger LOGGER = LoggerFactory.getLogger(WriteToBq.class);
//        options.setProject("symmetric-hull-368913");
//        options.setRunner(DataflowRunner.class);
//        options.setGcpTempLocation("gs://bucket_for_bqq/tempForBq");
//        options.setRegion("europe-west1");
//        options.setJobName("readFromPBWriteToBqWithTableROw");

    public static void main(String[] args) {
//        gender = "M";
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);


//        GetAllFromUserSessionTable.get(pipeline)
//                .apply(ParDo.of(new DoFn<UserSession, Void>() {
//                    @ProcessElement
//                    public void processElement(ProcessContext context) {
//                        System.out.println(Objects.requireNonNull(context.element()).toString());
//                    }
//
//                }));

//        GetAllFromUserTable.get(pipeline)
//                .apply(ParDo.of(new DoFn<User, Void>() {
//                    @ProcessElement
//                    public void apply(ProcessContext c){
//                       System.out.println(Objects.requireNonNull(c.element()).toString());
//                    }
//                }));
//
//        PCollection<Void> name = GetAllFromGymTable.get(pipeline)
//                .apply(ParDo.of(new DoFn<Gym, Void>() {
//                    @ProcessElement
//                    public void apply(ProcessContext c) {
//                        System.out.println(c.element());
//                    }
//                }));
//


//      pipeline.apply(  GetAllFromSessionTable.get())
//                        .apply(ParDo.of(new DoFn<Session, Session>() {
//                            @ProcessElement
//                            public void apply (ProcessContext c){
//                                Session session=c.element();
//                               //c.output(new Session((c.element()).getSessionType()));
//                                System.out.println(session.getSessionId());
//                            }
//                        }));
//pipeline.run();

//        PCollection<KV<String, Integer>> numPerSessionRepeat =
//                GetAllFromSessionTable.get(pipeline)
//
//                        .apply("KV Session , 1", ParDo.of(new SessionType()))
//                        .apply("count  session is repeated", GroupByKey.create())
//                        .apply(ParDo.of(new NumSession()));
//
//
//       PCollection<String>minRepeatSession= numPerSessionRepeat
//               .apply("min session repeat",ParDo.of(new MinSessionRepeat()));
//
//       PCollection<String>maxRepeatSession= numPerSessionRepeat
//               .apply("max session repeat",ParDo.of(new MaxSessionRepeat()));
//
//       PCollection<Double>averageNumSession= numPerSessionRepeat
//               .apply("Extract num sesion",ParDo.of(new ExtractOnlyNumSession()))
//               .apply("",Mean.globally());
//
//        PCollection<Double>medianNumSession= numPerSessionRepeat.apply(ParDo.of(new DoFn<KV<String, Integer>, Double>() {
//            @ProcessElement
//            public void apply (ProcessContext c){
//                c.output(Double.valueOf(c.element().getValue()));
//            }
//        })).apply(Combine.globally(new Median()));
//
//
//
//
//        UtilityMinMaxAvgMedian utilityMinMaxAvgMedian=new UtilityMinMaxAvgMedian();
//        utilityMinMaxAvgMedian.setPipeline(pipeline);
//        utilityMinMaxAvgMedian.getMinRepeatSession();


//       PCollection<KV<String, String>> userIdGenderOnlyMale= GetAllFromUserTable.get(pipeline)
//                        .apply("UserID ,genderMale ",ParDo.of(new KVUserGender()));
//
//        PCollection<KV<String, Integer>> userIdSessionId=GetAllFromUserSessionTable.get(pipeline)
//                .apply("UserId ,SessionId",ParDo.of(new KVUserIdSessionId()));
//
//        PCollection<KV<String, KV<String, Integer>>> joinedDatasets =
//                Join.innerJoin(
//                        userIdGenderOnlyMale, userIdSessionId);
//
//       PCollection<KV<String, Integer>>UserIdSessionIdOnlyMaleGender= joinedDatasets.apply(
//               MapElements.via(
//                new SimpleFunction<KV<String, KV<String, Integer>>, KV<String,Integer>>() {
//
//                    @Override
//                    public KV<String, Integer> apply(KV<String, KV<String, Integer>> input) {
////                        System.out.println(input.getKey() + ", " +
////                                  input.getValue().getValue());
//                        return KV.of(input.getKey(),input.getValue().getValue());//user id session id from user_session
//                    }
//
//                }));
//
//
//
//       PCollection<KV<String,Integer>>KVUserIdRAte=GetAllFromUserSessionTable.get(pipeline)
//               .apply("KV SessionId ,Rate ",ParDo.of(new KVSessionIdRate()));
//
//        PCollection<KV<String, KV<Integer, Integer>>> joinedDatasetsUserIdSessionIdOnlyMaleGenderKVUserIdRAte =
//                Join.innerJoin(
//                        UserIdSessionIdOnlyMaleGender, KVUserIdRAte);
//
//        PCollection<KV<String, Integer>>SessionIdRateOnlyMaleGender =joinedDatasetsUserIdSessionIdOnlyMaleGenderKVUserIdRAte
//                .apply(MapElements.via(
//                        new SimpleFunction<KV<String, KV<Integer, Integer>>, KV<String,Integer>>() {
//
//                            @Override
//                            public KV<String, Integer> apply(KV<String, KV<Integer, Integer>> input) {
//                             //   System.out.println(
//                         //               input.getValue().getKey() + ", " + input.getValue().getValue());
//                                return KV.of(String.valueOf(input.getValue().getKey()),input.getValue().getValue());//sessionId rate only male from userSession
//                            }
//
//                        }));
//
//
//
//
//         PCollectionList<KV<String, String>> getSessionTypeId=  GetAllFromSessionTable.get(pipeline)
//                        .apply(ParDo.of(new KVSessionIdSessionType()))
//                        .apply(Partition.of(
//                                6, new Partition.PartitionFn<KV<String, String>>() {
//                                    @Override
//                                    public int partitionFor(KV<String, String> elem, int numPartitions) {
//                                        if(elem.getValue().equals("YOGA")){
//                                            return 0;
//                                        }else if (elem.getValue().equals("ENDURANCE")){
//                                            return 1;
//                                        }else if (elem.getValue().equals("FARTLEK")){
//                                            return 2;
//                                        }else if(elem.getValue().equals("PERSONAL_TRAINING")) {
//                                            return 3;
//                                        }else if(elem.getValue().equals("CROSSFIT")) {
//                                            return 4;
//                                        }return 5;
//                                    }
//                                }
//                        ));
//
//
//        PCollection<KV<String, String>> IdSessionSessionTypeForYOGA= getSessionTypeId.get(0);
//        PCollection<KV<String, String>> IdSessionSessionTypeForENDURANCE= getSessionTypeId.get(1);
//        PCollection<KV<String, String>> IdSessionSessionTypeForFARTLEK= getSessionTypeId.get(2);
//        PCollection<KV<String, String>> IdSessionSessionTypeForPERSONAL_TRAINING= getSessionTypeId.get(3);
//        PCollection<KV<String, String>> IdSessionSessionTypeForCROSSFIT= getSessionTypeId.get(4);
//        PCollection<KV<String, String>> IdSessionSessionTypeForBODYBUILDING= getSessionTypeId.get(5);
//
//
//        PCollection<KV<String, KV<Integer, String>>> yogaRateOnlyMale= Join.rightOuterJoin(SessionIdRateOnlyMaleGender, IdSessionSessionTypeForYOGA, -1);
//        PCollection<KV<String, KV<Integer, String>>> enduranceRateOnlyMale= Join.rightOuterJoin(SessionIdRateOnlyMaleGender, IdSessionSessionTypeForENDURANCE, -1);
//        PCollection<KV<String, KV<Integer, String>>> fartlekRateOnlyMale= Join.rightOuterJoin(SessionIdRateOnlyMaleGender, IdSessionSessionTypeForFARTLEK, -1);
//        PCollection<KV<String, KV<Integer, String>>> personalTrainingRateOnlyMale= Join.rightOuterJoin(SessionIdRateOnlyMaleGender, IdSessionSessionTypeForPERSONAL_TRAINING, -1);
//        PCollection<KV<String, KV<Integer, String>>> crossFeetRateOnlyMale= Join.rightOuterJoin(SessionIdRateOnlyMaleGender, IdSessionSessionTypeForCROSSFIT, -1);
//        PCollection<KV<String, KV<Integer, String>>> bodyBuildingRateOnlyMale= Join.rightOuterJoin(SessionIdRateOnlyMaleGender, IdSessionSessionTypeForBODYBUILDING, -1);
//
//
//
//
//        PCollection<KV<String, Double>> yogaRateMale=  yogaRateOnlyMale.apply(ParDo.of(new ExtractRateName()))
//                .apply(Mean.perKey());
//
//        PCollection<KV<String, Double>> enduranceRateMale= enduranceRateOnlyMale.apply(ParDo.of(new ExtractRateName()))
//                .apply(Mean.perKey());
//
//        PCollection<KV<String, Double>> fartlekRateMale=fartlekRateOnlyMale.apply(ParDo.of(new ExtractRateName()))
//                .apply(Mean.perKey());
//
//        PCollection<KV<String, Double>> personalTrainingRateMale= personalTrainingRateOnlyMale.apply(ParDo.of(new ExtractRateName()))
//                .apply(Mean.perKey());
//
//        PCollection<KV<String, Double>> crossFeetRateMale= crossFeetRateOnlyMale.apply(ParDo.of(new ExtractRateName()))
//                .apply(Mean.perKey());
//
//        PCollection<KV<String, Double>> bodyBuildingRateMale= bodyBuildingRateOnlyMale.apply(ParDo.of(new ExtractRateName()))
//                .apply(Mean.perKey());
//
//
//
//        PCollectionList<KV<String, Double>> pSessionTypeRateFromMale=PCollectionList.of(yogaRateMale).and(enduranceRateMale).and(fartlekRateMale).and(personalTrainingRateMale).and(crossFeetRateMale).and(bodyBuildingRateMale);
//        PCollection<KV<String, Double>> flattenedAllsessionAvgRateFromMale=pSessionTypeRateFromMale.apply(Flatten.pCollections());
//        PCollection<Void> awfd=flattenedAllsessionAvgRateFromMale.apply(Combine.globally(Max.of(new KVComparator())))
//                .apply(ParDo.of(new DoFn<KV<String,Double>, Void>() {
//                    @ProcessElement
//                    public void apply(ProcessContext c){
//                        System.out.println(c.element());
//                    }
//                }));


//        pipeline.apply(GetAllFromSessionTable.get())
//                .apply(ParDo.of(new DoFn<Session, KV<String, String>>() {
//                    @ProcessElement
//                    public void aVoid(ProcessContext c) {
//                        Session session = c.element();
//                        //System.out.println(c.element());
//                        //System.out.println(KV.of(session.getStartTime(), String.valueOf(session.getSessionId())));
//                    }
//                }));
//
//        PCollection<KV<String, String>> KVSessionIdUserId = GetAllFromUserSessionTable.get(pipeline)
//                .apply(ParDo.of(new DoFn<UserSession, KV<String, String>>() {
//                    @ProcessElement
//                    public void aVoid(ProcessContext c) {
//                        UserSession userSession = c.element();
//                        c.output(KV.of(String.valueOf(userSession.getSessionId()), String.valueOf(userSession.getUserId())));
//                    }
//                }));
//
//        PCollection<KV<String, String>> joinedDatasets =
//                Join.innerJoin(
//                        KVTimeStartSessionID, KVSessionIdUserId)
//                        .apply("session time ,user id for each session",ParDo.of(new DoFn<KV<String, KV<String, String>>, KV<String, String>>() {
//                            @ProcessElement
//                            public void aVoid(ProcessContext c){
////                                System.out.println(c.element().getKey()+": "+c.element().getValue().getKey()+": "+
////                                        c.element().getValue().getValue());
//                                c.output(KV.of(c.element().getValue().getKey(),c.element().getValue().getValue()));//session time ,user id
//                                //session id ,session time ,user id
//                            }
//                        }));
//
//
//       // PCollection<KV<String, Integer>> maxNumSessionTimePrefered =
//                joinedDatasets.apply(GroupByKey.create())
//                .apply("num session id at different times happen",ParDo.of(new DoFn<KV<String, Iterable<String>>, KV<String, Double>>() {
//                    @ProcessElement
//                    public void aVoid(ProcessContext c) {
//                        String strKey = c.element().getKey();
//                        Iterable<String> strValue = c.element().getValue();
//                        Integer sum = 0;
//                        for (String loop : strValue) {
//                            sum++;
//                        }
//                        //System.out.println(KV.of(strKey, sum));
//                        c.output(KV.of(strKey, Double.valueOf(sum)));
//                    }
//                })).apply("most time frequent",Combine.globally(Max.of(new KVComparator())))
//                        .apply(ParDo.of(new DoFn<KV<String, Double>, Void>() {
//                            @ProcessElement
//                            public void aVoid(ProcessContext c){
//                                //System.out.println(c.element());
//                            }
//                        }));


//        UtilityTimeFrequent.mostTimeFrequent();
//        pipeline.run();

//        UtilsRateGender.setPipeline(pipeline);
//        UtilsRateGender.getBiggestRateGenderAvgOfAllTypeSession();
//        pipeline.run();

//        UtilityMinMaxAvgMedian utilityMinMaxAvgMedian=new UtilityMinMaxAvgMedian(pipeline);
//        utilityMinMaxAvgMedian.getNumPerSessionRepeat(pipeline);
//        pipeline.run();


//        UtilityMinMaxAvgMedian utilityMinMaxAvgMedian=new UtilityMinMaxAvgMedian(pipeline);
//        utilityMinMaxAvgMedian.getMinRepeatSession(pipeline);
//        pipeline.run();
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
        pipeline.apply(Create.of(1)).apply(ParDo.of(new DoFn<Integer, TableRow>() {
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

//                context.output(new TableRow()
//                        .set("maxDiffLevelYoga",maxDiffLevelYoga)
//                        .set("maxDiffLevelEndurance",maxDiffLevelEndurance)
//                        .set("maxDiffLevelFartlek",maxDiffLevelFartlek)
//                        .set("maxDiffLevelPersonalTraining",maxDiffLevelPersonalTraining)
//                        .set("maxDiffLevelCrossfit",maxDiffLevelCrossfit)
//                        .set("maxDiffLevelBodyBuilding",maxDiffLevelBodyBuilding)
//                        .set("minDiffLevelYoga",minDiffLevelYoga)
//                        .set("minDiffLevelEndurance",minDiffLevelEndurance)
//                        .set("minDiffLevelFartlek",minDiffLevelFartlek)
//                        .set("minDiffLevelPersonalTraining",minDiffLevelPersonalTraining)
//                        .set("minDiffLevelCrossfit",minDiffLevelCrossfit)
//                        .set("minDiffLevelBodyBuilding",minDiffLevelBodyBuilding)
//                        .set("medianDiffLevelYoga",medianDiffLevelYoga)
//                        .set("medianDiffLevelEndurance",medianDiffLevelEndurance)
//                        .set("medianDiffLevelFartlek",medianDiffLevelFartlek)
//                        .set("medianDiffLevelPersonalTraining",medianDiffLevelPersonalTraining)
//                        .set("medianDiffLevelCrossfit",medianDiffLevelCrossfit)
//                        .set("medianDiffLevelBodyBuilding",medianDiffLevelBodyBuilding)
//                        .set("avgDiffLevelYoga",avgDiffLevelYoga)
//                        .set("avgDiffLevelEndurance",avgDiffLevelEndurance)
//                        .set("avgDiffLevelFartlek",avgDiffLevelFartlek)
//                        .set("avgDiffLevelPersonalTraining",avgDiffLevelPersonalTraining)
//                        .set("avgDiffLevelCrossfit",avgDiffLevelCrossfit)
//                        .set("avgDiffLevelBodyBuilding",avgDiffLevelBodyBuilding)
//                        .set("mostTimeFrequentFromMale",mostTimeFrequentFromMale)
//                        .set("mostTimeFrequentFromFemale",mostTimeFrequentFromFemale)
//                        .set("minRepeatSession",minRepeatSession)
//                        .set("maxRepeatSession",maxRepeatSession)
//                        .set("avgRepeatSession",avgRepeatSession));

            }
        }).withSideInputs(maxDiffLevelYoga,maxDiffLevelEndurance,maxDiffLevelFartlek,maxDiffLevelPersonalTraining,maxDiffLevelCrossfit,maxDiffLevelBodyBuilding,
                minDiffLevelYoga,minDiffLevelEndurance,minDiffLevelFartlek,minDiffLevelPersonalTraining,minDiffLevelCrossfit,minDiffLevelBodyBuilding,
                medianDiffLevelYoga,medianDiffLevelEndurance,medianDiffLevelFartlek,medianDiffLevelPersonalTraining,medianDiffLevelCrossfit,medianDiffLevelBodyBuilding,
                avgDiffLevelYoga,avgDiffLevelEndurance,avgDiffLevelFartlek,avgDiffLevelPersonalTraining,avgDiffLevelCrossfit,avgDiffLevelBodyBuilding,
                mostTimeFrequentFromMale,mostTimeFrequentFromFemale,minRepeatSession,maxRepeatSession,avgRepeatSession
        ));
//                .apply(BigQueryIO.writeTableRows().to("symmetric-hull-368913.KVmodelYear.carModelYear")
//                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
//                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));
        // SessionStatistics statistics=new SessionStatistics();
//        System.out.println(UtiliyDifficultyLevel.getTableRowFromDifficulty(minDifficultyLevel,maxDifficultyLevel,avgDifficultyLevel,medianDifficultyLevel,statistics));

        pipeline.run();
       // System.out.println(avgSessionRepeat);

    }

}
