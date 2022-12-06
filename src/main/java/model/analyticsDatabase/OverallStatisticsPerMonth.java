package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallStatisticsPerMonth {
    private int id;
    private int year;
    private int month;
    private String mostFrequentGender;
    private int mostFrequentCustomerId;
    private String busiestTimeOfDay;
    private String favouriteSessionForMales;
    private String favouriteSessionForFemales;
    private String favouriteAttendanceTimeOfDayForFemales;
    private String favouriteAttendanceTimeOfDayForMales;
}
