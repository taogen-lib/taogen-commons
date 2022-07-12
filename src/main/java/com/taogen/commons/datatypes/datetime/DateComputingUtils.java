package com.taogen.commons.datatypes.datetime;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Taogen
 */
public class DateComputingUtils {

    public static Date addYears(Date date, int years) {
        return dateFieldAdd(date, Calendar.YEAR, years);
    }

    public static Date addMonths(Date date, int months) {
        return dateFieldAdd(date, Calendar.MONTH, months);
    }

    public static Date addDays(Date date, int days) {
        return dateFieldAdd(date, Calendar.DATE, days);
    }

    public static Date addHours(Date date, int hours) {
        return dateFieldAdd(date, Calendar.HOUR_OF_DAY, hours);
    }

    public static Date addMinutes(Date date, int minutes) {
        return dateFieldAdd(date, Calendar.MINUTE, minutes);
    }

    public static Date addSeconds(Date date, int seconds) {
        return dateFieldAdd(date, Calendar.SECOND, seconds);
    }

    public static Date dateFieldAdd(Date date, int calendarField, int addition) {
        if (date == null || !DateUtils.isLegalCalendarField(calendarField)) {
            throw new IllegalArgumentException("Illegal date or calendar field.");
        }
        if (addition == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, addition);
        return calendar.getTime();
    }

    public static long getDiffDays(Date firstDate, Date secondDate) throws IllegalArgumentException {
        if (firstDate == null || secondDate == null) {
            throw new IllegalArgumentException();
        }
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        //long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long diff = diffInMillies / DateUtils.DAY_MILLISECONDS;
        return diff;
    }
}
