package model.operationalDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    String Sessiontype;
    String dayWeek;
}
