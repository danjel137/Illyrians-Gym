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
}

