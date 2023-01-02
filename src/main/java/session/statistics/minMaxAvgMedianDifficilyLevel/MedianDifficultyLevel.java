package session.statistics.minMaxAvgMedianDifficilyLevel;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianDifficultyLevel extends DoFn<KV<String,Iterable<Integer>>,KV<String,Integer>> {
    @ProcessElement
    public void processElement(
            @Element KV<String,Iterable<Integer>>element,
            OutputReceiver<KV<String,Integer>>out){
        String sessionType=element.getKey();
        List<Integer> list=new ArrayList<>();
        for (Integer iterate:element.getValue()){
            list.add(iterate);
        }
        int median;
        Collections.sort(list);
                if(list.size()%2==1)
        {

            median=list.get((list.size()+1)/2-1);
        }
        else
        {
            median=(list.get(list.size()/2-1)+list.get(list.size()/2))/2;

        }
        System.out.println(KV.of(sessionType,median));

    }
}
//    public static void main(String arg[])
//    {
//        int n=5;
//        double a[]=new double[n];
//        a[0]=10;
//        a[1]=20;
//        a[2]=30;
//        a[3]=40;
//        a[4]=50;
//
//        double m=0;
//        if(n%2==1)
//        {
//            m=a[(n+1)/2-1];
//        }
//        else
//        {
//            m=(a[n/2-1]+a[n/2])/2;
//        }
//
//        System.out.println("Median :"+m);
//    }
//}