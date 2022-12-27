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


  public static PCollection<KV<String, Integer>> getUserIdSessionIdOnlyMaleGenderFromUserSession(Pipeline pipeline) {
    PCollection<KV<String, String>> userIdGenderOnlyMale = pipeline.apply(GetAllFromUserTable.getTransform())
        .apply("UserID ,genderMale ", ParDo.of(new KVUserGender()));

    PCollection<KV<String, Integer>> userIdSessionId = pipeline.apply(GetAllFromUserSessionTable.getTransform())
        .apply("UserId ,SessionId", ParDo.of(new KVUserIdSessionId()));

    PCollection<KV<String, KV<String, Integer>>> joinedDatasets =
        Join.innerJoin(
            userIdGenderOnlyMale, userIdSessionId);

    return joinedDatasets.apply("TEST",
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


  public static PCollection<KV<String, Integer>> getSessionIdRateOnlyMaleGenderFromUserSession(Pipeline pipeline) {
    PCollection<KV<String, Integer>> KVUserIdRAte = pipeline.apply(GetAllFromUserSessionTable.getTransform())
        .apply("KV SessionId ,Rate ", ParDo.of(new KVSessionIdRate()));

    PCollection<KV<String, KV<Integer, Integer>>> joinedDatasetsUserIdSessionIdOnlyMaleGenderKVUserIdRAte =
        Join.innerJoin(
            getUserIdSessionIdOnlyMaleGenderFromUserSession(pipeline), KVUserIdRAte);

    return joinedDatasetsUserIdSessionIdOnlyMaleGenderKVUserIdRAte
        .apply("FGCFXBFFSDFHGDFHDF", MapElements.via(
            new SimpleFunction<KV<String, KV<Integer, Integer>>, KV<String, Integer>>() {

              @Override
              public KV<String, Integer> apply(KV<String, KV<Integer, Integer>> input) {
                //   System.out.println(
                //               input.getValue().getKey() + ", " + input.getValue().getValue());
                return KV.of(String.valueOf(input.getValue().getKey()), input.getValue().getValue());//sessionId rate only male from userSession
              }

            }));
  }

  public static PCollectionList<KV<String, String>> getSessionIdSessionTypeFromSession(Pipeline pipeline) {
    return pipeline.apply(GetAllFromSessionTable.getTransform())
        .apply("Shko meeer", ParDo.of(new KVSessionIdSessionType()))
        .apply("Dim ne pa shkolll jemi", Partition.of(
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


  public static PCollectionList<KV<String, KV<Integer, String>>> getRateForSpecificGenderForAllSessionType(Pipeline pipeline) {

    PCollection<KV<String, KV<Integer, String>>> yogaRateOnlyGender = Join.rightOuterJoin(
        getSessionIdRateOnlyMaleGenderFromUserSession(pipeline),
        getSessionIdSessionTypeFromSession(pipeline).get(0), -1);
    PCollection<KV<String, KV<Integer, String>>> enduranceRateOnlyGender = Join.rightOuterJoin(
        getSessionIdRateOnlyMaleGenderFromUserSession(pipeline),
        getSessionIdSessionTypeFromSession(pipeline).get(1), -1);
    PCollection<KV<String, KV<Integer, String>>> fartlekRateOnlyGender = Join.rightOuterJoin(
        getSessionIdRateOnlyMaleGenderFromUserSession(pipeline),
        getSessionIdSessionTypeFromSession(pipeline).get(2), -1);
    PCollection<KV<String, KV<Integer, String>>> personalTrainingRateOnlyGender = Join.rightOuterJoin(
        getSessionIdRateOnlyMaleGenderFromUserSession(pipeline),
        getSessionIdSessionTypeFromSession(pipeline).get(3), -1);
    PCollection<KV<String, KV<Integer, String>>> crossFeetRateOnlyGender = Join.rightOuterJoin(
        getSessionIdRateOnlyMaleGenderFromUserSession(pipeline),
        getSessionIdSessionTypeFromSession(pipeline).get(4), -1);
    PCollection<KV<String, KV<Integer, String>>> bodyBuildingRateOnlyGender = Join.rightOuterJoin(
        getSessionIdRateOnlyMaleGenderFromUserSession(pipeline),
        getSessionIdSessionTypeFromSession(pipeline).get(5), -1);
    return PCollectionList.of(yogaRateOnlyGender)
        .and(enduranceRateOnlyGender)
        .and(fartlekRateOnlyGender)
        .and(personalTrainingRateOnlyGender)
        .and(crossFeetRateOnlyGender)
        .and(bodyBuildingRateOnlyGender);

  }

  public static PCollectionList<KV<String, Double>> getAvgRateForSpecificGenderForAllSessionType(Pipeline pipeline) {
    PCollection<KV<String, Double>> yogaRateMale = getRateForSpecificGenderForAllSessionType(pipeline).get(0).apply("1", ParDo.of(new ExtractRateName()))
        .apply("7", Mean.perKey());

    PCollection<KV<String, Double>> enduranceRateMale = getRateForSpecificGenderForAllSessionType(pipeline).get(1).apply("2", ParDo.of(new ExtractRateName()))
        .apply("8", Mean.perKey());

    PCollection<KV<String, Double>> fartlekRateMale = getRateForSpecificGenderForAllSessionType(pipeline).get(2).apply("3", ParDo.of(new ExtractRateName()))
        .apply("9", Mean.perKey());

    PCollection<KV<String, Double>> personalTrainingRateMale = getRateForSpecificGenderForAllSessionType(pipeline).get(3).apply("4", ParDo.of(new ExtractRateName()))
        .apply("41", Mean.perKey());

    PCollection<KV<String, Double>> crossFeetRateMale = getRateForSpecificGenderForAllSessionType(pipeline).get(4).apply("5", ParDo.of(new ExtractRateName()))
        .apply("52", Mean.perKey());

    PCollection<KV<String, Double>> bodyBuildingRateMale = getRateForSpecificGenderForAllSessionType(pipeline).get(5).apply("6", ParDo.of(new ExtractRateName()))
        .apply("61", Mean.perKey());

    return PCollectionList.of(yogaRateMale)
        .and(enduranceRateMale)
        .and(fartlekRateMale)
        .and(personalTrainingRateMale)
        .and(crossFeetRateMale)
        .and(bodyBuildingRateMale);
  }

  public static void getBiggestRateGenderAvgOfAllTypeSession(Pipeline pipeline) {
    PCollection<KV<String, Double>> flattenedAllsessionAvgRateFromMale =
        getAvgRateForSpecificGenderForAllSessionType(pipeline).apply("zonja naile", Flatten.pCollections());
    flattenedAllsessionAvgRateFromMale
        .apply("ZHGARRAVINA", Combine.globally(Max.of(new KVComparator())))
        .apply("DFOGODFGNKDF", ParDo.of(new DoFn<KV<String, Double>, Void>() {

          @ProcessElement
          public void apply(ProcessContext c) {
            avgTotal = c.element().getKey() + ": " + c.element().getValue();
            System.out.println(c.element());
          }
        }));

  }
}
