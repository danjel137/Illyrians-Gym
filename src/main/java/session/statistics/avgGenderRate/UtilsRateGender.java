package session.statistics.avgGenderRate;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import data.dataFromOperationalDB.GetAllFromUserSessionTable;
import data.dataFromOperationalDB.GetAllFromUserTable;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.joinlibrary.Join;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

import java.io.Serializable;

public class UtilsRateGender implements Serializable {

    public static String avgTotal;
    public static Pipeline pipeline;

    public static void setPipeline(Pipeline pipeline) {
        UtilsRateGender.pipeline = pipeline;
    }

    public static PCollection<KV<String, Integer>> getUserIdSessionIdOnlyGenderSpecificFromUserSession() {
        PCollection<KV<String, String>> userIdGenderOnlyMale = GetAllFromUserTable.get(pipeline)
                .apply("UserID ,genderMale ", ParDo.of(new KVUserGender()));

        PCollection<KV<String, Integer>> userIdSessionId = GetAllFromUserSessionTable.get(pipeline)
                .apply("UserId ,SessionId" , ParDo.of(new KVUserIdSessionId()));

        PCollection<KV<String, KV<String, Integer>>> joinedDatasets =
                Join.innerJoin(
                        userIdGenderOnlyMale, userIdSessionId);

        return joinedDatasets.apply("//user id session id from user_session",
                MapElements.via(
                        new SimpleFunction<KV<String, KV<String, Integer>>, KV<String, Integer>>() {

                            @Override
                            public KV<String, Integer> apply(KV<String, KV<String, Integer>> input) {
//                        System.out.println(input.getKey() + ", " +
//                                  input.getValue().getValue());
                                return KV.of(input.getKey(), input.getValue().getValue());//user id session id from user_session
                            }

                        }));
    }


    public static PCollection<KV<String, Integer>> getSessionIdRateOnlyMaleGenderFromUserSession() {
        PCollection<KV<String, Integer>> KVUserIdRAte = GetAllFromUserSessionTable.get(pipeline)
                .apply("KV SessionId ,Rate " , ParDo.of(new KVSessionIdRate()));

        PCollection<KV<String, KV<Integer, Integer>>> joinedDatasetsUserIdSessionIdOnlyMaleGenderKVUserIdRAte =
                Join.innerJoin(
                        getUserIdSessionIdOnlyGenderSpecificFromUserSession(), KVUserIdRAte);

        return joinedDatasetsUserIdSessionIdOnlyMaleGenderKVUserIdRAte
                .apply("KV sessionId rate only gender specified", MapElements.via(
                        new SimpleFunction<KV<String, KV<Integer, Integer>>, KV<String, Integer>>() {

                            @Override
                            public KV<String, Integer> apply(KV<String, KV<Integer, Integer>> input) {
                                //   System.out.println(
                                //               input.getValue().getKey() + ", " + input.getValue().getValue());
                                return KV.of(String.valueOf(input.getValue().getKey()), input.getValue().getValue());//sessionId rate only male from userSession
                            }

                        }));
    }

    public static PCollectionList<KV<String, String>> getSessionIdSessionTypeFromSession() {
        return GetAllFromSessionTable.get(pipeline)
                .apply("KV sessionId sessionType", ParDo.of(new KVSessionIdSessionType()))
                .apply("split session type in partition", Partition.of(
                        6, new Partition.PartitionFn<KV<String, String>>() {
                            @Override
                            public int partitionFor(KV<String, String> elem, int numPartitions) {
                                if (elem.getValue().equals("YOGA")) {
                                    return 0;
                                } else if (elem.getValue().equals("ENDURANCE")) {
                                    return 1;
                                } else if (elem.getValue().equals("FARTLEK")) {
                                    return 2;
                                } else if (elem.getValue().equals("PERSONAL_TRAINING")) {
                                    return 3;
                                } else if (elem.getValue().equals("CROSSFIT")) {
                                    return 4;
                                }
                                return 5;
                            }
                        }
                ));
    }


    public static PCollectionList<KV<String, KV<Integer, String>>> getRateForSpecificGenderForAllSessionType() {

        PCollection<KV<String, KV<Integer, String>>> yogaRateOnlyGender = Join.rightOuterJoin(getSessionIdRateOnlyMaleGenderFromUserSession(), getSessionIdSessionTypeFromSession().get(0), -1);
        PCollection<KV<String, KV<Integer, String>>> enduranceRateOnlyGender = Join.rightOuterJoin(getSessionIdRateOnlyMaleGenderFromUserSession(), getSessionIdSessionTypeFromSession().get(1), -1);
        PCollection<KV<String, KV<Integer, String>>> fartlekRateOnlyGender = Join.rightOuterJoin(getSessionIdRateOnlyMaleGenderFromUserSession(), getSessionIdSessionTypeFromSession().get(2), -1);
        PCollection<KV<String, KV<Integer, String>>> personalTrainingRateOnlyGender = Join.rightOuterJoin(getSessionIdRateOnlyMaleGenderFromUserSession(), getSessionIdSessionTypeFromSession().get(3), -1);
        PCollection<KV<String, KV<Integer, String>>> crossFeetRateOnlyGender = Join.rightOuterJoin(getSessionIdRateOnlyMaleGenderFromUserSession(), getSessionIdSessionTypeFromSession().get(4), -1);
        PCollection<KV<String, KV<Integer, String>>> bodyBuildingRateOnlyGender = Join.rightOuterJoin(getSessionIdRateOnlyMaleGenderFromUserSession(), getSessionIdSessionTypeFromSession().get(5), -1);
        return PCollectionList.of(yogaRateOnlyGender)
                .and(enduranceRateOnlyGender)
                .and(fartlekRateOnlyGender)
                .and(personalTrainingRateOnlyGender)
                .and(crossFeetRateOnlyGender)
                .and(bodyBuildingRateOnlyGender);

    }

    public static PCollectionList<KV<String, Double>> getAvgRateForSpecificGenderForAllSessionType() {
        PCollection<KV<String, Double>> yogaRateAvg = getRateForSpecificGenderForAllSessionType().get(0).apply("KV rate name", ParDo.of(new ExtractRateName()))
                .apply("Avg yoga", Mean.perKey());

        PCollection<KV<String, Double>> enduranceRateAvg = getRateForSpecificGenderForAllSessionType().get(1).apply("KV rate name", ParDo.of(new ExtractRateName()))
                .apply("Avg endurance", Mean.perKey());

        PCollection<KV<String, Double>> fartlekRateAvg = getRateForSpecificGenderForAllSessionType().get(2).apply("KV rate name", ParDo.of(new ExtractRateName()))
                .apply("Avg fartlek", Mean.perKey());

        PCollection<KV<String, Double>> personalTrainingRateAvg = getRateForSpecificGenderForAllSessionType().get(3).apply("KV rate name", ParDo.of(new ExtractRateName()))
                .apply("Avg personal training", Mean.perKey());

        PCollection<KV<String, Double>> crossFeetRateAvg = getRateForSpecificGenderForAllSessionType().get(4).apply("KV rate name", ParDo.of(new ExtractRateName()))
                .apply("Avg crossFeet", Mean.perKey());

        PCollection<KV<String, Double>> bodyBuildingRateAvg = getRateForSpecificGenderForAllSessionType().get(5).apply("KV rate name", ParDo.of(new ExtractRateName()))
                .apply("avg bodybuilding", Mean.perKey());

        return PCollectionList.of(yogaRateAvg)
                .and(enduranceRateAvg)
                .and(fartlekRateAvg)
                .and(personalTrainingRateAvg)
                .and(crossFeetRateAvg)
                .and(bodyBuildingRateAvg);
    }

    public static void getBiggestRateGenderAvgOfAllTypeSession() {
        PCollection<KV<String, Double>> flattenedAllsessionAvgRateFromMale = getAvgRateForSpecificGenderForAllSessionType().apply( Flatten.pCollections());
        flattenedAllsessionAvgRateFromMale
                .apply( Combine.globally(Max.of(new KVComparator())))
                .apply( ParDo.of(new DoFn<KV<String, Double>, Void>() {

                    @ProcessElement
                    public void apply(ProcessContext c) {
                        avgTotal = c.element().getKey() + ": " + c.element().getValue();
                        System.out.println(c.element());
                    }
                }));

    }
}
