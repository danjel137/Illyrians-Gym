package model.analyticsDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarRatingStatisticsPerMonth {
    private int id;
    private int year;
    private int month;
    private double minStar;
    private double maxStar;
    private double medianStar;
    private double averageStar;
}
