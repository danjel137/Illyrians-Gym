package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsStatisticsPerMonth {
    private int id;
    private int year;
    private int month;
    private String sessionType;
    private short minNumParticipantsPerDay;
    private short maxNumParticipantsPerDay;
    private float meanNumParticipants;
    private float averageNumParticipants;
}
