package com.taogen.commons.datatypes.datetime;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Taogen
 */
public class DateComputingUtils {

    public static Date addHours(Date date, int hours) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
