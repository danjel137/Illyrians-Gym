package data.dataFromOperationalDB;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

import java.io.Serializable;

public interface JDBCInputFactorySessionTable extends Serializable {
    static PTransform<PBegin, PCollection<Session>> get() {
        return null;
    }
}
