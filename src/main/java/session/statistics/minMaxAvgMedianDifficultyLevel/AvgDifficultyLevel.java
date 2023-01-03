package session.statistics.minMaxAvgMedianDifficultyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class AvgDifficultyLevel extends DoFn<KV<String,Iterable<Integer>>,KV<String,Double>> {
    @ProcessElement
    public void processElement(
            @Element KV<String,Iterable<Integer>>element,
            OutputReceiver<KV<String,Double>>out){
        String sessionType=element.getKey();
        int count=0;
        double diffLevel=0;

        for (Integer iter:element.getValue()){
            diffLevel+=iter;
            count++;
        }
        //System.out.println(KV.of(sessionType,diffLevel/count));
        out.output(KV.of(sessionType, diffLevel/count));
    }
}
