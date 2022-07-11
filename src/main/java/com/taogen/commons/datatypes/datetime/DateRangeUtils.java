package com.taogen.commons.datatypes.datetime;

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
}
