package model.analyticsDatabase;

import java.io.Serializable;

public class HeadersOfCsvFiles implements Serializable {
    private HeadersOfCsvFiles() {
    }

    public static String participantStatisticsHeaders = "id,sessionType," +
            "minNumParticipants," +
            "maxNumParticipants," +
            "medianNumParticipants," +
            "averageNumParticipants," +
            "mostFrequentGender," +
            "mostFrequentPersonNameSurname," +
            "mostFrequentMaleNameSurname," +
            "mostFrequentFemaleNameSurname," +
            "resultDay";

    public static String sessionStatisticsHeaders = "id," +
            "minCountPerDayOfWeek," +
            "maxCountPerDayOfWeek," +
            "averageCountPerDayOfWeek," +
            "medianCountPerDayOfWeek," +
            "busiestPartOfDay," +
            "mostFrequentTypeOfSession," +
            "favouriteSessionForMales," +
            "favouriteSessionForFemales," +
            "favouriteAttendanceTimeForMales," +
            "favouriteAttendanceTimeForFemales," +
            "maxDifficultyLevel," +
            "minDifficultyLevel," +
            "averageDifficultyLevel," +
            "medianDifficultyLevel," +
            "resultDay";

    public static String starRatingStatistics = " id," +
            "year," +
            "month," +
            "minStar," +
            "maxStar," +
            "medianStar," +
            "averageStar";
}
