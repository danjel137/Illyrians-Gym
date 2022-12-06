package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarReviewStatisticsPerMonth {
    private int id;
    private int year;
    private int month;
    private short minStar;
    private short maxStar;
    private float meanStar;
    private float averageStar;
}
