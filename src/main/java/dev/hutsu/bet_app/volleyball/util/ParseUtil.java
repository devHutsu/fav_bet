package dev.hutsu.bet_app.volleyball.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParseUtil {


    public static String titleCountry(String source){
        String[] tmp = source.split("\\|");
        return tmp[0].trim();
    }
    public static String titleLeague(String source){
        String[] tmp = source.split("\\|");
        return tmp[1].trim();
    }

    public static String urlEvent(String source){
        String[] tmp = source.split("-");
        return String.format("https://www.favbet.ua/uk/sports/event/volleyball/%s/", tmp[2]);
    }

    public static String urlLiveEvent(Integer id){
        return String.format("https://www.favbet.ua/uk/live/event/volleyball/%s/", id);
    }

    public static Integer getData_id(String source){
        String[] tmp = source.split("-");
        return Integer.parseInt(tmp[2]);
    }

    public static LocalDateTime dateEvent(String date, String time){
        LocalDateTime tmpLocalDateTime = LocalDateTime.now();
        String[] tmpDate = date.split("\\.");
        String[] tmpTime = time.split(":");

        int monthNow = tmpLocalDateTime.getMonthValue();
        int yearNow = tmpLocalDateTime.getYear();
        if (Integer.parseInt(tmpDate[1]) < monthNow){
            yearNow+=1;
        }

        return LocalDateTime.of(yearNow, Integer.parseInt(tmpDate[1]),
                Integer.parseInt(tmpDate[0]), Integer.parseInt(tmpTime[0]), Integer.parseInt(tmpTime[1]));
    }


    public static void main(String[] args) {

        System.out.println(ParseUtil.dateEvent("14.02", "9:30"));
    }





}
