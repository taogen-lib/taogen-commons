package com.taogen.commons.datatypes.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Taogen
 */
public class DateFormatters {
    public static final DateFormat yyyy_MM_dd_HH_mm_ss_SSS_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static final DateFormat yyyy_MM_dd_HH_mm_ss_SSS_2 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

    public static final DateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final DateFormat yyyy_MM_dd_HH_mm_ss_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat yyyy_MM_dd_HH_mm_ss_2 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static final DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final DateFormat yyyy_MM_dd_1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat yyyy_MM_dd_2 = new SimpleDateFormat("yyyy/MM/dd");

    public static final DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat yyyy_MM_1 = new SimpleDateFormat("yyyy-MM");

    public static final DateFormat yyyy_MM_2 = new SimpleDateFormat("yyyy/MM");

    public static final DateFormat yyyyMM = new SimpleDateFormat("yyyyMM");

    public static final DateFormat MM_dd_1 = new SimpleDateFormat("MM-dd");

    public static final DateFormat MM_dd_2 = new SimpleDateFormat("MM/dd");

    public static final DateFormat MMdd = new SimpleDateFormat("MMdd");
}
