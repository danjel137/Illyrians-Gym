package service.utilities;

import model.analytics.db.ParticipantsStatistics;
import model.analytics.db.SessionStatistics;
import model.analytics.db.StarRatingStatisticsPerMonth;

public class CSVUtils {
    private CSVUtils() {
    }

    public static String toCsvLine(StarRatingStatisticsPerMonth input) {
        return input.getId() + "," +
                input.getMonth() + ","
                + input.getYear() + ","
                + input.getMinStar() + ","
                + input.getMaxStar() + ","
                + input.getMedianStar() + ","
                + input.getAverageStar();
    }

    public static String toCsvLine(SessionStatistics input) {
        return input.getId() + "," +
                input.getMinCountPerDayOfWeek() + "," +
                input.getMaxCountPerDayOfWeek() + "," +
                input.getAverageCountPerDayOfWeek() + "," +
                input.getMedianDifficultyLevel() + "," +
                input.getBusiestPartOfDay() + "," +
                input.getMostFrequentTypeOfSession() + "," +
                input.getFavouriteSessionForMales() + "," +
                input.getFavouriteSessionForFemales() + "," +
                input.getFavouriteAttendanceTimeForMales() + "," +
                input.getFavouriteSessionForFemales() + "," +
                input.getMaxDifficultyLevel() + "," +
                input.getMinDifficultyLevel() + "," +
                input.getAverageDifficultyLevel() + "," +
                input.getMedianDifficultyLevel() + "," +
                input.getResultDay();
    }

    public static String toCsvLine(ParticipantsStatistics input) {
        return input.getId() + "," +
                input.getSessionType() + "," +
                input.getMinNumParticipants() + "," +
                input.getMaxNumParticipants() + "," +
                input.getMedianNumParticipants() + "," +
                input.getAverageNumParticipants() + "," +
                input.getMostFrequentGender() + "," +
                input.getMostFrequentPersonNameSurname() + "," +
                input.getMostFrequentMaleNameSurname() + "," +
                input.getMostFrequentFemaleNameSurname() + "," +
                input.getResultDay();
    }
}
