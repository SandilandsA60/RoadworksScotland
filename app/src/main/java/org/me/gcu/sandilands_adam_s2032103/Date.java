package org.me.gcu.sandilands_adam_s2032103;
// Adam Sandilands s2032103
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Date {

    public Calendar convertRssDateToCalendarObj(String date) {
        String shortDate = convertLongDateToShort(date);
        Calendar calender = convertStringToDate(shortDate);
        return calender;
    }

    public long numberOfDays(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start)) + 1;
    }

    public String getMonth(String month) {
        switch(month) {
            case "January":
                return "01";
            case "February":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            default:
                return "";
        }
    }


    public String convertLongDateToShort(String dateString) {
        String regex = "\\d+";
        String result = "";
        String[] words = dateString.split(" ");
        for (String word : words) {
            if (word.matches(regex)) {
                result+= word+ "/";
            } else if(!getMonth(word).isEmpty()) {
                result+= getMonth(word) + "/";
            }
        }
        return result.substring(0, result.length()-1);
    }



    public Calendar convertStringToDate(String dateString) {
        String[] numbers = dateString.split("/");
        int day = Integer.parseInt(numbers[0]);
        int month = Integer.parseInt(numbers[1]);
        int year = Integer.parseInt(numbers[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }


}
