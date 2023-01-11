import junit.framework.TestCase;
import junitparams.JUnitParamsRunner;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.coders.VarIntCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.TestStream;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import session.statistics.MinMaxAvgMedianSessionRepeat.UtilityMinMaxAvgMedianRepeat;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class TestAvgGenderRAte extends TestCase implements Serializable {
    @Rule
    public final transient TestPipeline pipeline = TestPipeline.create().enableAbandonedNodeEnforcement(false);
    ;

    private final List<KV<String, Integer>> records;

    public TestAvgGenderRAte(List<KV<String, Integer>> records) {
        this.records = records;
    }


    @Test
    public void give_when_expect() {
        //PCollection<Integer> create = pipeline.apply("FirstCreate", Create.of(1, 2, 3));
       // UtilityMinMaxAvgMedianRepeat utilityMinMaxAvgMedianRepeat = new UtilityMinMaxAvgMedianRepeat(pipeline);

       // PCollection<String> givenOutput = utilityMinMaxAvgMedianRepeat.getMinRepeatSession(pipeline);
//
//
       // PAssert.that(givenOutput).containsInAnyOrder("ENDURANCE: 6", "PERSONAL_TRAINING: 6", "FARTLEK: 6", "CROSSFIT: 6", "YOGA: 6", "BODYBUILDING: 6");

//
//        PAssert.that(givenOutput ).containsInAnyOrder( "1", "2");
        pipeline.run().waitUntilFinish();
    }

    @Test
    public void give_when_expectt() {
//        PCollection<KV<String, Integer>> givenOutput = TestStream.create(StringUtf8Coder.of()).addElements(KV.of("YOGA", 6), KV.of("ENDURANCE", 1))
//                .advanceWatermarkToInfinity();

        PCollection<KV<String, Integer>> inputtt =
                pipeline
                        .apply(
                                TestStream.create(KvCoder.of(StringUtf8Coder.of(), VarIntCoder.of()))
                                        .addElements(KV.of("FARTLEK",4))
                                        .addElements(KV.of("ENDURANCE",2))
                                        .addElements(KV.of("BODYBUILDING",3))
                                        .addElements(KV.of("CROSSFIT",2))

                        .advanceWatermarkToInfinity());

//        FakePubsubOutputFactory outputFactory =
//                new FakePubsubOutputFactory((SerializableFunction<PAssert.IterableAssert<String>, PAssert.IterableAssert<String>>)
//                        input -> input.satisfies((SerializableFunction<Iterable<String>, Void>) input1 -> {
//                            Iterator<String> iter = input1.iterator();
//                            assertTrue(iter.hasNext());
//                            Set<String> elems = new HashSet<>();
//                            elems.add(iter.next());
//                            assertTrue(iter.hasNext());
//                            elems.add(iter.next());
//                            assertEquals(new HashSet<KV<String,Integer>>() {{
//                                add(KV.of("ENDURANCE",2));
//                                add(KV.of("BODYBUILDING",3));
//                            }}, elems);
//                            assertFalse(iter.hasNext());
//                            return null;
//                        }));


         UtilityMinMaxAvgMedianRepeat utilityMinMaxAvgMedianRepeat = new UtilityMinMaxAvgMedianRepeat(pipeline);
         PCollection<String> givenOutput = utilityMinMaxAvgMedianRepeat.getMinRepeatSession(pipeline);
                PAssert.that(givenOutput ).containsInAnyOrder( "ENDURANCE: 6", "PERSONAL_TRAINING: 6", "FARTLEK: 6", "CROSSFIT: 6", "YOGA: 6", "BODYBUILDING: 6");

        pipeline.run().waitUntilFinish();


    }
}
