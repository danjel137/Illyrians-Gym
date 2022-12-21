package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsStatistics implements Serializable {
    private int id;
    private String sessionType;
    private short minNumParticipants;
    private short maxNumParticipants;
    private float medianNumParticipants;
    private float averageNumParticipants;
    private String mostFrequentGender;
    private String mostFrequentPersonNameSurname;
    private String mostFrequentMaleNameSurname;
    private String mostFrequentFemaleNameSurname;
    private Date resultDay;
}
