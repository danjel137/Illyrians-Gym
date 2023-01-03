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
    private String id;

    private int minDifficultyLevelYoga;
    private int maxDifficultyLevelYoga;
    private int medianDifficultyLevelYoga;
    private double avgDifficultyLevelYoga;
    private int minDifficultyLevelEndurance;
    private int maxDifficultyLevelEndurance;
    private double medianDifficultyLevelEndurance;
    private double avgDifficultyLevelEndurance;
    private int minDifficultyLevelCrossFit;
    private int maxDifficultyLevelCrossFit;
    private double medianDifficultyLevelCrossFit;
    private double avgDifficultyLevelCrossFit;
    private int minDifficultyLevelPersonalTraining;
    private int maxDifficultyLevelPersonalTraining;
    private int medianDifficultyLevelPersonalTraining;
    private double avgDifficultyLevelPersonalTraining;
    private int minDifficultyLevelBODYBUILDING;
    private int maxDifficultyLevelBODYBUILDING;
    private double medianDifficultyLevelBODYBUILDING;
    private double avgDifficultyLevelBODYBUILDING;
    private int minDifficultyLevelFARTLEK;
    private int maxDifficultyLevelFARTLEK;
    private double medianDifficultyLevelFARTLEK;
    private double avgDifficultyLevelFARTLEK;
    private String minSessionRepeat;
    private String maxSessionRepeat;
    private String avgSessionRepeat;
    private String mostTimeFrequentGenderFemale;
    private String mostTimeFrequentGenderMale;
//    private short minCountPerDayOfWeek;
//    private short maxCountPerDayOfWeek;
//    private float averageCountPerDayOfWeek;
//    private float medianCountPerDayOfWeek;
//    private String busiestPartOfDay;
//    private String mostFrequentTypeOfSession;
//    private String favouriteSessionForMales;
//    private String favouriteSessionForFemales;
//    private String favouriteAttendanceTimeForMales;
//    private String favouriteAttendanceTimeForFemales;
//    private int maxDifficultyLevel;
//    private int minDifficultyLevel;
//    private int averageDifficultyLevel;
//    private int medianDifficultyLevel;
    private Date resultDay;
}
