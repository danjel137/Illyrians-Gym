package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionStatistics implements Serializable {
    private int id;
    private short minCountPerDayOfWeek;
    private short maxCountPerDayOfWeek;
    private float averageCountPerDayOfWeek;
    private float medianCountPerDayOfWeek;
    private String busiestPartOfDay;
    private String mostFrequentTypeOfSession;
    private String favouriteSessionForMales;
    private String favouriteSessionForFemales;
    private String favouriteAttendanceTimeForMales;
    private String favouriteAttendanceTimeForFemales;
    private int maxDifficultyLevel;
    private int minDifficultyLevel;
    private int averageDifficultyLevel;
    private int medianDifficultyLevel;
    private Date resultDay;
}
