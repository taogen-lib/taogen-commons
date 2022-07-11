package com.taogen.commons.datatypes.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils
{
    public static final DateFormat FORMAT_YYYYMMMDD = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat FORMAT_YYYY_MM_DD_1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat FORMAT_YYYY_MM_DD_2 = new SimpleDateFormat("yyyy/MM/dd");

    public static final DateFormat FORMAT_YYYYMM = new SimpleDateFormat("yyyyMM");
    public static final DateFormat FORMAT_YYYYMM_1 = new SimpleDateFormat("yyyy-MM");
    public static final DateFormat FORMAT_YYYYMM_2 = new SimpleDateFormat("yyyy/MM");

    public static final DateFormat FORMAT_MMDD = new SimpleDateFormat("MMdd");
    public static final DateFormat FORMAT_MMDD_1 = new SimpleDateFormat("MM-dd");
    public static final DateFormat FORMAT_MMDD_2 = new SimpleDateFormat("MM/dd");

    public static final DateFormat FORMAT_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final long DAY_MILLISECONDS = 24 * 60 * 60 * 1000;

    // Convert <<

    public static String getDateFormatStr(Date date, DateFormat format)
    {
        String result = null;
        if (date != null && format != null)
        {
            result = format.format(date);
        }
        return result;
    }

    public static Date getDateByStr(String dateStr, DateFormat format)
    {
        Date date = null;
        if (dateStr != null && format != null)
        {
            try
            {
                date = format.parse(dateStr);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static String convertDateStr(String dateStr, DateFormat fromFormat, DateFormat toFormat)
    {
        String result = null;
        if (dateStr != null && fromFormat != null && toFormat != null)
        {
            result = DateUtils.getDateFormatStr(DateUtils.getDateByStr(dateStr, fromFormat), toFormat);
        }
        return result;
    }

    // >> Convert

    // ----------------------------------------------------------------

    // Calculate <<

    public static int getField(Date date, int calendarField) throws IllegalArgumentException
    {
        if (date == null || ! isLegalCalendarField(calendarField))
        {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendarField);
    }

    public static Date add(Date date, int calendarField, int addtion) throws IllegalArgumentException
    {
        if (date == null || ! isLegalCalendarField(calendarField))
        {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, addtion);
        return calendar.getTime();
    }

    public static long getDiffDays(Date firstDate, Date secondDate) throws IllegalArgumentException
    {
        if (firstDate == null || secondDate == null)
        {
            throw new IllegalArgumentException();
        }
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        //long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long diff = diffInMillies / DateUtils.DAY_MILLISECONDS;
        return diff;
    }

    public static List<String> getBetweenDates(Date startDate, Date endDate, DateFormat formatter)
    {
        return new ArrayList<>(getBetweens(startDate, endDate, formatter, Calendar.DAY_OF_MONTH));
    }

    public static List<String> getBetweenMonths(Date startDate, Date endDate, DateFormat formatter)
    {
        return new ArrayList<>(getBetweens(startDate, endDate, formatter, Calendar.MONTH));
    }

    public static Date getFirstDayOfWeek(Date date) throws IllegalArgumentException
    {
        Calendar calendar = getCalendarWithDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth (Date date) throws IllegalArgumentException
    {
        Calendar calendar = getCalendarWithDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek(Date date) throws IllegalArgumentException
    {
        Calendar calendar = getCalendarWithDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        for (int i = 1; i < 7; i++)
        {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth (Date date) throws IllegalArgumentException
    {
        Calendar calendar = getCalendarWithDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static int getWeekOfMonth (Date date) throws IllegalArgumentException
    {
        Calendar calendar = getCalendarWithDate(date);
        calendar.setMinimalDaysInFirstWeek(1);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public static int getWeekOfYear(Date date) throws IllegalArgumentException
    {
        Calendar calendar = getCalendarWithDate(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    // >> Calculate


    // ----------------------------------------------------------------

    // assist <<

    public static boolean isLegalCalendarField(int calendarField)
    {
        boolean result = false;
        if (calendarField >= 0 || calendarField < Calendar.FIELD_COUNT)
        {
            result = true;
        }
        return result;
    }

    public static Calendar getCalendarWithDate(Date date)
    {
        if (date == null)
        {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static List<String> getBetweens(Date startDate, Date endDate, DateFormat formatter,
         int calendarField) throws IllegalArgumentException
    {
        if (startDate == null || endDate == null || endDate.before(startDate) || formatter == null
                || ! isLegalCalendarField(calendarField))
        {
            throw new IllegalArgumentException();
        }
        List<String> result = new LinkedList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(endDate);
        while (calendar2.compareTo(calendar1) >= 0)
        {
            result.add(0,formatter.format(calendar2.getTime()));
            calendar2.add(calendarField, -1);
        }
        return result;
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
