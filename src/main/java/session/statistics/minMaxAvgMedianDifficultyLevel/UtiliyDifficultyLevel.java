package session.statistics.minMaxAvgMedianDifficultyLevel;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import model.analyticsDatabase.SessionStatistics;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

import java.io.Serializable;
import java.util.List;

public class UtiliyDifficultyLevel implements Serializable {
    public static PCollection<KV<String, Iterable<Integer>>> sessionTypeDifficultyLevel(Pipeline pipeline) {
        return pipeline.apply("get all from session table", GetAllFromSessionTable.get())
                .apply("KV session type difficulty level", ParDo.of(new KVSessionTypeDifficultyLevel()))
                .apply(GroupByKey.create());
    }

    public static PCollectionList<KV<String, Integer>> sessionTypeMaxDifficultyLevel(Pipeline pipeline) {
        return sessionTypeDifficultyLevel(pipeline)
                .apply("max difficulty level per session type", ParDo.of(new MaxDifficultyLevel()))
                .apply(Partition.of(6,new PartitionSessionType()));
    }

    public static PCollectionList<KV<String, Integer>> sessionTypeMinDifficultyLevel(Pipeline pipeline) {
        return sessionTypeDifficultyLevel(pipeline)
                .apply("max difficulty level per session type", ParDo.of(new MinDifficultyLevel()))
                .apply(Partition.of(6,new PartitionSessionType()));
    }

    public static PCollectionList<KV<String, Integer>> sessionTypeMedianDifficultyLevel(Pipeline pipeline) {
        return sessionTypeDifficultyLevel(pipeline)
                .apply("median difficulty level per session type", ParDo.of(new MedianDifficultyLevel()))
                .apply(Partition.of(6,new PartitionSessionType()));

    }

    public static PCollectionList<KV<String, Double>> sessionTypeAvgDifficultyLevel(Pipeline pipeline) {
        return sessionTypeDifficultyLevel(pipeline)
                .apply("avg difficulty level per session type", ParDo.of(new AvgDifficultyLevel()))
                .apply("split session type in partition", Partition.of(
                        6, new Partition.PartitionFn<KV<String, Double>>() {
                            @Override
                            public int partitionFor(KV<String, Double> elem, int numPartitions) {
                                if (elem.getKey().equals("YOGA")) {
                                    return 0;
                                } else if (elem.getKey().equals("ENDURANCE")) {
                                    return 1;
                                } else if (elem.getKey().equals("FARTLEK")) {
                                    return 2;
                                } else if (elem.getKey().equals("PERSONAL_TRAINING")) {
                                    return 3;
                                } else if (elem.getKey().equals("CROSSFIT")) {
                                    return 4;
                                }
                                return 5;
                            }
                        }
                ));
    }



//    public static SessionStatistics getTableRowFromDifficulty(List<KV<String, Integer>> minDifficultyLevel,
//                                                              List<KV<String, Integer>> maxDifficultyLevel,
//                                                              List<KV<String, Double>> avgDifficultyLevel,
//                                                              List<KV<String, Integer>> medianDifficultyLevel,
//                                                              SessionStatistics sessionStatistics){
//        for (int i = 0; i < avgDifficultyLevel.size(); i++){
//            if (minDifficultyLevel.get(i).getKey().equals("YOGA")) {
//                sessionStatistics.setMinDifficultyLevelYoga(minDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMaxDifficultyLevelYoga(maxDifficultyLevel.get(i).getValue());
//                sessionStatistics.setAvgDifficultyLevelYoga(avgDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMedianDifficultyLevelYoga(medianDifficultyLevel.get(i).getValue());
//            } else if (minDifficultyLevel.get(i).getKey().equals("ENDURANCE")) {
//                sessionStatistics.setMinDifficultyLevelEndurance(minDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMaxDifficultyLevelEndurance(maxDifficultyLevel.get(i).getValue());
//                sessionStatistics.setAvgDifficultyLevelEndurance(avgDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMedianDifficultyLevelEndurance(medianDifficultyLevel.get(i).getValue());
//            } else if (minDifficultyLevel.get(i).getKey().equals("FARTLEK")) {
//                sessionStatistics.setMinDifficultyLevelFARTLEK(minDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMaxDifficultyLevelFARTLEK(maxDifficultyLevel.get(i).getValue());
//                sessionStatistics.setAvgDifficultyLevelFARTLEK(avgDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMedianDifficultyLevelFARTLEK(medianDifficultyLevel.get(i).getValue());
//            } else if (minDifficultyLevel.get(i).getKey().equals("PERSONAL_TRAINING")) {
//                sessionStatistics.setMinDifficultyLevelPersonalTraining(minDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMaxDifficultyLevelPersonalTraining(maxDifficultyLevel.get(i).getValue());
//                sessionStatistics.setAvgDifficultyLevelPersonalTraining(avgDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMedianDifficultyLevelPersonalTraining(medianDifficultyLevel.get(i).getValue());
//            } else if (minDifficultyLevel.get(i).getKey().equals("CROSSFIT")) {
//                sessionStatistics.setMinDifficultyLevelCrossFit(minDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMaxDifficultyLevelCrossFit(maxDifficultyLevel.get(i).getValue());
//                sessionStatistics.setAvgDifficultyLevelCrossFit(avgDifficultyLevel.get(i).getValue());
//                sessionStatistics.setMedianDifficultyLevelCrossFit(medianDifficultyLevel.get(i).getValue());
//            }else
//            sessionStatistics.setMinDifficultyLevelBODYBUILDING(minDifficultyLevel.get(i).getValue());
//            sessionStatistics.setMaxDifficultyLevelBODYBUILDING(maxDifficultyLevel.get(i).getValue());
//            sessionStatistics.setAvgDifficultyLevelBODYBUILDING(avgDifficultyLevel.get(i).getValue());
//            sessionStatistics.setMedianDifficultyLevelBODYBUILDING(medianDifficultyLevel.get(i).getValue());
//        }
//        return sessionStatistics;
//    }
}

