package model.operational.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session implements Serializable {
    int sessionId;
    String description;
    int difficultyLevel;
    int durationMins;
    int maxNumParticipants;
    String startTime;
    String sessionType;
    String dayWeek;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId == session.sessionId && difficultyLevel == session.difficultyLevel && durationMins == session.durationMins && maxNumParticipants == session.maxNumParticipants && Objects.equals(description, session.description) && Objects.equals(startTime, session.startTime) && Objects.equals(sessionType, session.sessionType) && Objects.equals(dayWeek, session.dayWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, description, difficultyLevel, durationMins, maxNumParticipants, startTime, sessionType, dayWeek);
    }
}
