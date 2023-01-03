package session.statistics.minMaxAvgMedianDifficultyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class MinDifficultyLevel extends DoFn<KV<String,Iterable<Integer>>,KV<String,Integer>> {
    @ProcessElement
    public void processElement(
            @Element KV<String,Iterable<Integer>>element,
            OutputReceiver<KV<String,Integer>>out){
        String sessionType=element.getKey();
        int min=Integer.MAX_VALUE;

        for (Integer compare:element.getValue()){
            if(compare<min){
                min=compare;
            }
        }
        //System.out.println(sessionType+min);
        out.output(KV.of(sessionType,min));
    }
}
