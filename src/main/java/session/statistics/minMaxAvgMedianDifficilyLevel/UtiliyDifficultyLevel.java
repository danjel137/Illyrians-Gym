package session.statistics.minMaxAvgMedianDifficilyLevel;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import session.statistics.avgGenderRate.KVSessionIdSessionType;

import java.io.Serializable;

public class UtiliyDifficultyLevel implements Serializable {
   public static  PCollection <KV<String,Iterable<Integer>>>sessionTypeDifficultyLevel(Pipeline pipeline){
     return   pipeline.apply("get all from session table", GetAllFromSessionTable.get())
               .apply("KV session type difficulty level",ParDo.of(new KVSessionTypeDifficultyLevel()))
               .apply(GroupByKey.create());
   }

   public static PCollection <KV<String,Integer>> sessionTypeMaxDifficultyLevel(Pipeline pipeline){
       return sessionTypeDifficultyLevel(pipeline)
               .apply("max difficulty level per session type",ParDo.of(new MaxDifficultyLevel()));
   }

    public static PCollection <KV<String,Integer>> sessionTypeMinDifficultyLevel(Pipeline pipeline){
        return sessionTypeDifficultyLevel(pipeline)
                .apply("max difficulty level per session type",ParDo.of(new MinDifficultyLevel()));
    }

    public static PCollection <KV<String,Double>> sessionTypeAvgDifficultyLevel(Pipeline pipeline){
        return sessionTypeDifficultyLevel(pipeline)
                .apply("avg difficulty level per session type",ParDo.of(new AvgDifficultyLevel()));
    }

    public static PCollection <KV<String,Integer>> sessionTypeMedianDifficultyLevel(Pipeline pipeline){
        return sessionTypeDifficultyLevel(pipeline)
                .apply("median difficulty level per session type",ParDo.of(new MedianDifficultyLevel()));
    }


    }

