package data.dataFromOperationalDB;

import model.operationalDatabase.Gym;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public interface JDBCInputFactoryGymTable extends Serializable {
    static PTransform<PBegin, PCollection<Gym>> get() {
        return null;
    }
}
