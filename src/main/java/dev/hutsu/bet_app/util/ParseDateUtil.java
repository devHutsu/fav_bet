package dev.hutsu.bet_app.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParseDateUtil {

    private final static String TODAY = "Сегодня";
    private final static String Tomorrow = "Завтра";


    public static LocalDateTime parseDate(String content){

        LocalDate localDateTime = LocalDate.now();

        String[] tmp = content.split("/");
        if (tmp.length > 0) {
            tmp[0] = tmp[0].trim();
            tmp[1] = tmp[1].trim();

            String[] tmpTime = tmp[1].split(":");
            int hour = Integer.parseInt(tmpTime[0]);
            int minute = Integer.parseInt(tmpTime[1]);


            return switch (tmp[0]) {
                case TODAY -> localDateTime.atTime(hour, minute);
                case Tomorrow -> localDateTime.plusDays(1).atTime(hour, minute);
                default -> null;
            };
        }
        return null;
    }
}
