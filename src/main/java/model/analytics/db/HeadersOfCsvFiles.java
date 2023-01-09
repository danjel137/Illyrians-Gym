package model.analytics.db;

import java.io.Serializable;

public class HeadersOfCsvFiles implements Serializable {
    private HeadersOfCsvFiles() {
    }

    public static final String PARTICIPANT_STATISTICS_HEADERS = "id,sessionType," +
            "minNumParticipants," +
            "maxNumParticipants," +
            "medianNumParticipants," +
            "averageNumParticipants," +
            "mostFrequentGender," +
            "mostFrequentPersonNameSurname," +
            "mostFrequentMaleNameSurname," +
            "mostFrequentFemaleNameSurname," +
            "resultDay";

    public static final String SESSION_STATISTICS_HEADERS = "id," +
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

    public static final String STAR_RATING_STATISTICS = " id," +
            "year," +
            "month," +
            "minStar," +
            "maxStar," +
            "medianStar," +
            "averageStar";
}
