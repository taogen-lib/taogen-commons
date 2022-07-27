package com.taogen.commons.datatypes.datetime;

import java.text.DateFormat;
import java.util.*;

/**
 * @author Taogen
 */
public class DateRangeUtils {
    /**
     * Contains the start date and end date of a date range.
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<String> getDateStringsBetweenDates(Date beginDate, Date endDate, String formatStr) {
        if (beginDate == null || endDate == null || beginDate.after(endDate)) {
            return Collections.emptyList();
        }
        Calendar beginDateCal = Calendar.getInstance();
        beginDateCal.setTime(beginDate);
        DateUtils.clearCalendarTimeSection(beginDateCal);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);
        DateUtils.clearCalendarTimeSection(endDateCal);
        List<String> resultList = new ArrayList<>();
        while (beginDateCal.before(endDateCal)) {
            resultList.add(DateConversionUtils.formatDate(beginDateCal.getTime(), formatStr));
            beginDateCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        resultList.add(DateConversionUtils.formatDate(endDateCal.getTime(), formatStr));
        return resultList;
    }

    public static List<String> getBetweens(Date startDate, Date endDate, DateFormat formatter,
                                           int calendarField) throws IllegalArgumentException {
        if (startDate == null || endDate == null || endDate.before(startDate) || formatter == null
                || !DateUtils.isLegalCalendarField(calendarField)) {
            throw new IllegalArgumentException();
        }
        List<String> result = new LinkedList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(endDate);
        while (calendar2.compareTo(calendar1) >= 0) {
            result.add(0, formatter.format(calendar2.getTime()));
            calendar2.add(calendarField, -1);
        }
        return result;
    }

    public static List<String> getBetweenDates(Date startDate, Date endDate, DateFormat formatter) {
        return new ArrayList<>(getBetweens(startDate, endDate, formatter, Calendar.DAY_OF_MONTH));
    }

    public static List<String> getBetweenMonths(Date startDate, Date endDate, DateFormat formatter) {
        return new ArrayList<>(getBetweens(startDate, endDate, formatter, Calendar.MONTH));
    }

    public static boolean isBetweenHour(Date date, int startHour, int endHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour >= startHour && hour <= endHour;
    }
}
