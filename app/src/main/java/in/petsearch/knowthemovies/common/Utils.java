package in.petsearch.knowthemovies.common;

public class Utils {
    public static String getDaySuffix(final int n) {
        if(n>=11&&n<=13) {
            return "th";
        }
        switch (n%10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }

    public static String getMonth(final int n) {
        switch (n) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "";
        }
    }
}
