package session.statistics.minMaxAvgMedianDifficultyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.values.KV;

public class PartitionSessionType implements Partition.PartitionFn<KV<String, Integer>> {


    @Override
    public int partitionFor(KV<String, Integer> elem, int numPartitions) {
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
