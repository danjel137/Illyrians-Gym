package session.statistics.minMaxAvgMedianDifficilyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Min;
import org.apache.beam.sdk.values.KV;

public class MaxDifficultyLevel extends DoFn<KV<String,Iterable<Integer>>,KV<String,Integer>> {
    @ProcessElement
    public void processElement(
            @Element KV<String,Iterable<Integer>>element,
            OutputReceiver<KV<String,Integer>>out){
        String sessionType=element.getKey();
        int maxx=Integer.MIN_VALUE;

        for (Integer compare:element.getValue()){
            if(compare>maxx){
                maxx=compare;
            }
        }
        System.out.println(sessionType+maxx);
        out.output(KV.of(sessionType,maxx));
    }
}
