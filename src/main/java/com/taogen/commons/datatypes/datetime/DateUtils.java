package com.taogen.commons.datatypes.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final long DAY_MILLISECONDS = 24 * 60 * 60 * 1000;

    // Convert <<

    public static String getDateFormatStr(Date date, DateFormat format) {
        String result = null;
        if (date != null && format != null) {
            result = format.format(date);
        }
        return result;
    }

    public static Date getDateByStr(String dateStr, DateFormat format) {
        Date date = null;
        if (dateStr != null && format != null) {
            try {
                date = format.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static String convertDateStr(String dateStr, DateFormat fromFormat, DateFormat toFormat) {
        String result = null;
        if (dateStr != null && fromFormat != null && toFormat != null) {
            result = DateUtils.getDateFormatStr(DateUtils.getDateByStr(dateStr, fromFormat), toFormat);
        }
        return result;
    }

    // >> Convert

    // ----------------------------------------------------------------

    // Calculate <<

    public static int getField(Date date, int calendarField) throws IllegalArgumentException {
        if (date == null || !isLegalCalendarField(calendarField)) {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendarField);
    }

    public static Date getFirstDayOfWeek(Date date) throws IllegalArgumentException {
        Calendar calendar = getCalendarWithDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) throws IllegalArgumentException {
        Calendar calendar = getCalendarWithDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek(Date date) throws IllegalArgumentException {
        Calendar calendar = getCalendarWithDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) throws IllegalArgumentException {
        Calendar calendar = getCalendarWithDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static int getWeekOfMonth(Date date) throws IllegalArgumentException {
        Calendar calendar = getCalendarWithDate(date);
        calendar.setMinimalDaysInFirstWeek(1);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public static int getWeekOfYear(Date date) throws IllegalArgumentException {
        Calendar calendar = getCalendarWithDate(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    // >> Calculate


    // ----------------------------------------------------------------

    // assist <<

    public static boolean isLegalCalendarField(int calendarField) {
        return calendarField >= 0 || calendarField < Calendar.FIELD_COUNT;
    }

    public static Calendar getCalendarWithDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    // >> assist

    /**
     * For example: 2022-07-11 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartOfTheDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        clearCalendarTimeSection(calendar);
        return calendar.getTime();
    }

    public static void clearCalendarTimeSection(Calendar calendar) {
        if (calendar != null) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.clear(Calendar.MINUTE);
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MILLISECOND);
        }
    }

    /**
     * For example: 2022-07-11 23:59:59
     *
     * @param date
     * @return
     */
    public static Object getEndOfTheDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
}
