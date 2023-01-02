package data.dataFromOperationalDB;

import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public interface JDBCInputFactoryUserSessionTable extends Serializable {
    static PTransform<PBegin, PCollection<UserSession>> get() {
        return null;
    }
}
