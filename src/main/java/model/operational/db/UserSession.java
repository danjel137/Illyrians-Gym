package model.operational.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession implements Serializable {
    int userId;
    int sessionId;
    double rate;
    String dateRegisteredSession;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSession that = (UserSession) o;
        return userId == that.userId && sessionId == that.sessionId && Double.compare(that.rate, rate) == 0 && Objects.equals(dateRegisteredSession, that.dateRegisteredSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionId, rate, dateRegisteredSession);
    }
}
