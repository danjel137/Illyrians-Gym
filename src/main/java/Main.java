import data.dataFromOperationalDB.GetAllFromGymTable;
import data.dataFromOperationalDB.GetAllFromSessionTable;
import data.dataFromOperationalDB.GetAllFromUserSessionTable;
import data.dataFromOperationalDB.GetAllFromUserTable;
import model.operationalDatabase.Gym;
import model.operationalDatabase.Session;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.joinlibrary.Join;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import session.statistics.MinMaxAvgMedianSession.*;
import session.statistics.avgGenderRate.*;

import static session.statistics.avgGenderRate.KVUserGender.gender;


public class Main {

    public static void main(String[] args) {
        gender ="M";
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
//        GetAllFromSessionTable.get(pipeline)
//                        .apply(ParDo.of(new DoFn<Session, Session>() {
//                            @ProcessElement
//                            public void apply (ProcessContext c){
//                               c.output(new Session((c.element()).getSessionType()));
//                                System.out.println(c.element());
//                            }
//                        }));


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




//        UtilityMinMaxAvgMedian utilityMinMaxAvgMedian=new UtilityMinMaxAvgMedian();
//        utilityMinMaxAvgMedian.setPipeline(pipeline);
//        utilityMinMaxAvgMedian.getMinRepeatSession();


//
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
//                        //System.out.println(c.element());
//                    }
//                }));
        UtilsRateGender.getBiggestRateGenderAvgOfAllTypeSession(pipeline);
        pipeline.run();
    }
}
