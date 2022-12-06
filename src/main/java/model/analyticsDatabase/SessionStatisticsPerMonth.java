package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionStatisticsPerMonth {
    private int id;
    private int year;
    private int month;
    private short minCountDay;
    private short maxCountPerDay;
    private float averageCountPerDay;
    private float meanCountPerDay;
}
