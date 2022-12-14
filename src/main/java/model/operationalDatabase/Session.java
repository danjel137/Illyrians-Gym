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
    String type;
    String difficultyLevel;
    String startTime;
    int durationMins;
    int maxNumParticipants;
    String dayWeek;
}
