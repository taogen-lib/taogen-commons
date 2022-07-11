package com.taogen.commons.datatypes.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Taogen
 */
public class DateConversionUtils {

    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    public static Date parseDate(String dateStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
