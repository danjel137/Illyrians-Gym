package model.utilities;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class IdWrapper implements Serializable {
    private AtomicInteger uniqueId;

    public IdWrapper(int initialValueForId) {
        uniqueId = new AtomicInteger(initialValueForId);
    }

    public AtomicInteger getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(AtomicInteger uniqueId) {
        this.uniqueId = uniqueId;
    }
}
