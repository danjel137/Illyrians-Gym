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
    String title;
    String type;
    String difficultyLevel;
    String timeDate;
    int numParticipants;
    int length;
    int starReviews;
}
