package session.statistics.MinMaxAvgMedianSessionRepeat;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class NumSession extends DoFn<KV<String, Iterable<Integer>>, KV<String, Integer>> {
   public static int max = Integer.MIN_VALUE;
   public static int min = Integer.MAX_VALUE;

    @ProcessElement
    public void processElement(
            @Element KV<String, Iterable<Integer>> element,
            OutputReceiver<KV<String, Integer>> out) {
        String nameSession = element.getKey();
        int countNumSession = 0;


        for (Integer num : element.getValue()) {
            countNumSession += num;

        }
        if(countNumSession > max)
            max = countNumSession;

        if(countNumSession < min)
            min = countNumSession;

        out.output(KV.of(nameSession, countNumSession));
        //System.out.println(KV.of(nameSession,countNumSession));
       // System.out.println(countNumSession);
    }
}
