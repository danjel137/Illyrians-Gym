package service;

public class GetFromValidTimeStamp {

    private GetFromValidTimeStamp() {
    }

    public static String date(String validTimeStamp) {
        return validTimeStamp.split(" ")[0];
    }

    public static String time(String validTimeStamp) {
        return validTimeStamp.split(" ")[1].split("\\.")[0];
    }

    public static String month(String validTimeStamp) {
        return validTimeStamp.split(" ")[0].split("-")[1];
    }

    public static String year(String validTimeStamp) {
        return validTimeStamp.split(" ")[0].split("-")[0];
    }

    public static String yearMonth(String validTimeStamp) {
        String[] date = validTimeStamp.split(" ")[0].split("-");
        return date[0] + "-" + date[1];
    }

}
