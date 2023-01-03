package session.statistics.MinMaxAvgMedianSessionRepeat;

import org.apache.beam.sdk.transforms.SerializableFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Median implements SerializableFunction<Iterable<Double>, Double> {
    List<Double> li = new ArrayList<>();

    @Override
    public Double apply(Iterable<Double> input) {
        //System.out.println(input);
        while (input.iterator().hasNext()) {
            li.add(input.iterator().next());
        }
        Collections.sort(li);
        System.out.println(li);
        if (li.size() % 2 != 0) {
          //  System.out.println(li.get(li.size() / 2));
            return li.get(li.size() / 2);
        }
        //System.out.println(li.get((li.size()-1)/2)+li.get((li.size()/2)/2));
        return  li.get((li.size()-1)/2)+li.get((li.size()/2)/2);
    }


}

