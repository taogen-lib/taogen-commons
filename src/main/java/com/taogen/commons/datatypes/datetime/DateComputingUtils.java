package com.taogen.commons.datatypes.datetime;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Taogen
 */
public class DateComputingUtils {

    public static Date addYears(Date date, int years) {
        if (date == null || years == 0) {
            return date;
        }
        return dateFieldAdd(date, Calendar.YEAR, years);
    }

    public static Date addMonths(Date date, int months) {
        if (date == null || months == 0) {
            return date;
        }
        return dateFieldAdd(date, Calendar.MONTH, months);
    }

    public static Date addDays(Date date, int days) {
        if (date == null || days == 0) {
            return date;
        }
        return dateFieldAdd(date, Calendar.DATE, days);
    }

    public static Date addHours(Date date, int hours) {
        if (date == null || hours == 0) {
            return date;
        }
        return dateFieldAdd(date, Calendar.HOUR_OF_DAY, hours);
    }

    public static Date addMinutes(Date date, int minutes) {
        if (date == null || minutes == 0) {
            return date;
        }
        return dateFieldAdd(date, Calendar.MINUTE, minutes);
    }

    public static Date addSeconds(Date date, int seconds) {
        if (date == null || seconds == 0) {
            return date;
        }
        return dateFieldAdd(date, Calendar.SECOND, seconds);
    }

    private static Date dateFieldAdd(Date date, int calendarField, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, num);
        return calendar.getTime();
    }
}
