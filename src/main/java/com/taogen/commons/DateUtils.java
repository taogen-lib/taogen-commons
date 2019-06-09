package com.taogen.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    // TODO remove it
    public static void main(String[] args)
    {
//        String result = DateUtils.getDateFormatStr(DateUtils.getDateByStr("2019-05-01", FORMAT_YYYY_MM_DD_1), FORMAT_YYYYMMMDD);
//        System.out.println(StringUtils.str2Int(result, -1));
//        System.out.println(StringUtils.str2Int(convertDateStr("2019-05-02", FORMAT_YYYY_MM_DD_1, FORMAT_YYYYMMMDD), -1));
//        System.out.println(new SimpleDateFormat("yyyy-123-dd").format(new Date()));
        System.out.println(add(new Date(), 111, 1));
        Integer.parseInt("12");
    }


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
        if (date == null || calendarField < 0 || calendarField >= Calendar.FIELD_COUNT)
        {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendarField);
    }

    public static Date add(Date date, int calendarField, int addtion)
    {
        if (date == null)
        {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, addtion);
        return calendar.getTime();
    }

    public static int getDiff(Date startDate, Date endDate, int calendarField)
    {
        return -1;
    }

    public static List<String> getBetweenDates(Date startDate, Date endDate, DateFormat format)
    {
        return new ArrayList<>();
    }
    public static List<String> getBetweenMonths(Date startDate, Date endDate, DateFormat format)
    {
        return new ArrayList<>();
    }

    public static Date getFirstDayOfWeek(Date date)
    {
        return new Date();
    }

    public static Date getFirstDayOfMonth (Date date)
    {
        return new Date();
    }

    public static Date getLastDayOfWeek(Date date)
    {
        return new Date();
    }

    public static Date getLastDayOfMonth (Date date)
    {
        return new Date();
    }

    public static int getWeekNumofMonth (Date date)
    {
        return -1;
    }
    public static int getWeekNumOfYear(Date date)
    {
        return -1;
    }

    // >> Calculate
}
