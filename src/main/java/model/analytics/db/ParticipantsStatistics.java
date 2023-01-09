package model.analytics.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsStatistics implements Serializable {
    private int id;
    private String sessionType;
    private long minNumParticipants;
    private long maxNumParticipants;
    private double medianNumParticipants;
    private double averageNumParticipants;
    private String mostFrequentGender;
    private String mostFrequentPersonNameSurname;
    private String mostFrequentMaleNameSurname;
    private String mostFrequentFemaleNameSurname;
    private String resultDay;
}
