package com.hotel.hotel.util;

import java.time.DayOfWeek;

public class CustomDateUtils {

    public static Boolean isWeekendDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY) return true;

        if (dayOfWeek == DayOfWeek.SUNDAY) return true;

        return false;
    }
}
