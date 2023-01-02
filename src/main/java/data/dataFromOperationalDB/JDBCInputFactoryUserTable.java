package data.dataFromOperationalDB;

import model.operationalDatabase.User;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public interface JDBCInputFactoryUserTable extends Serializable {
    static PTransform<PBegin, PCollection<User>> get() {
        return null;
    }
}
