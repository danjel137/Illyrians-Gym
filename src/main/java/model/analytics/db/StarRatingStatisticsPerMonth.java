package model.analytics.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarRatingStatisticsPerMonth implements Serializable {
    private int id;
    private int year;
    private int month;
    private double minStar;
    private double maxStar;
    private double medianStar;
    private double averageStar;
}
