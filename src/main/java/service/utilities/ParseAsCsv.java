package service.utilities;

import model.analyticsDatabase.ParticipantsStatistics;
import model.analyticsDatabase.SessionStatistics;
import model.analyticsDatabase.StarRatingStatisticsPerMonth;

public class ParseAsCsv {
    private ParseAsCsv() {
    }
    public static String parse(StarRatingStatisticsPerMonth input) {
        return input.getId() + "," +
                input.getMonth() + ","
                + input.getYear() + ","
                + input.getMinStar() + ","
                + input.getMaxStar() + ","
                + input.getMedianStar() + ","
                + input.getAverageStar();
    }

    public static String parse(SessionStatistics input) {
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

    public static String parse(ParticipantsStatistics input) {
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
