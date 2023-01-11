import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

public class FakeOutputFactory {
    private final transient SerializableFunction<PAssert.IterableAssert<String>, PAssert.IterableAssert<String>> iterableChecker;

    public FakeOutputFactory(SerializableFunction<PAssert.IterableAssert<String>, PAssert.IterableAssert<String>> iterableChecker) {
        this.iterableChecker = iterableChecker;
    }

    public PTransform<PCollection<String>, PDone> writer() {
        return new PTransform<PCollection<String>, PDone>() {
            private static final long serialVersionUID = -1L;

            @Override
            public PDone expand(PCollection<String> input) {
                iterableChecker.apply(PAssert.that(input));
                return PDone.in(input.getPipeline());
            }
        };
    }
}
