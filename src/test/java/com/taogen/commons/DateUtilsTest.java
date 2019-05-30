package com.taogen.commons;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static com.taogen.commons.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest
{
    // Convert <<

    @Test
    public void getDateFormatStrTest()
    {
        // illegal params
        assertNull(getDateFormatStr(null, FORMAT_YYYY_MM_DD_1));
        // expected result
        assertEquals(FORMAT_YYYY_MM_DD_1.format(new Date()), getDateFormatStr(new Date(), FORMAT_YYYY_MM_DD_1));
    }

    @Test
    public void getDateByStrTest()
    {
        // illegal params
        assertNull(getDateByStr(null, FORMAT_YYYY_MM_DD_1));
        assertNull(getDateByStr("2019-01-01", null));
        assertNull(getDateByStr("2019-13-01", null));
        assertNull(getDateByStr("2019-13-01", FORMAT_YYYY_MM_DD_2));
        assertNotNull(getDateByStr("2019-13-01", FORMAT_YYYY_MM_DD_1));
        // expected result
        assertTrue(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1) instanceof Date);
    }

    @Test
    public void convertDateStrTest()
    {
        // illegal params
        assertNull(convertDateStr(null, FORMAT_YYYY_MM_DD_1, FORMAT_YYYY_MM_DD_2));
        assertNull(convertDateStr("2019-01-01", null, FORMAT_YYYY_MM_DD_2));
        assertNull(convertDateStr("2019-01-01", FORMAT_YYYY_MM_DD_1, null));
        assertNotNull(convertDateStr("2019-01-01", FORMAT_YYYY_MM_DD_1, FORMAT_YYYY_MM_DD_2));
        // expected result
        assertEquals("2019-01-01", convertDateStr("2019/01/01", FORMAT_YYYY_MM_DD_2, FORMAT_YYYY_MM_DD_1));
    }

    // >> Convert

    // ----------------------------------------------------------------

    // Calculate <<

    @Test
    public void getFieldTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> { getField(null, Calendar.DAY_OF_MONTH); });
        getField(null, 123456); // TODO
        // expected result
        assertEquals(2019, getField(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1), Calendar.YEAR));
    }

    @Test
    public void addTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ add(null, Calendar.DAY_OF_MONTH, 1); });
        assertThrows(IllegalArgumentException.class, () ->{ add(new Date(), 123456, 1); });
        // expected result
        assertEquals(getDateByStr("2019-01-02", FORMAT_YYYY_MM_DD_1),
                add(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1), Calendar.DAY_OF_MONTH, 1));
    }

    @Test
    public void getDiffTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {getDiff(null, new Date(), Calendar.DAY_OF_MONTH); });
        assertThrows(IllegalArgumentException.class, () -> {getDiff(new Date(), null, Calendar.DAY_OF_MONTH); });
        assertThrows(IllegalArgumentException.class, () -> {getDiff(new Date(), new Date(), 123456); });
        // expected result
        assertEquals(3, getDiff(
                getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-04", FORMAT_YYYY_MM_DD_1),
                Calendar.DAY_OF_MONTH
        ));
    }

    @Test
    public void getBetweenDatesTest()
    {
        // illegal params
        assertNull(getBetweenDates(null, new Date(), FORMAT_YYYY_MM_DD_1));
        assertNull(getBetweenDates(new Date(), null, FORMAT_YYYY_MM_DD_1));
        assertNull(getBetweenDates(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-02", FORMAT_YYYY_MM_DD_1), null));

        // expected result
        assertNotNull(getBetweenDates(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-03", FORMAT_YYYY_MM_DD_1),FORMAT_YYYY_MM_DD_1));
        assertTrue(getBetweenDates(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-03", FORMAT_YYYY_MM_DD_1),FORMAT_YYYY_MM_DD_1).size() == 2);
    }

    @Test
    public void getBetweenMonthsTest()
    {
        // illegal params
        // expected result
    }

    @Test
    public void getFirstDayOfWeekTest()
    {
        // illegal params
        // expected result
    }

    @Test
    public void getFirstDayOfMonthTest()
    {
        // illegal params
        // expected result
    }

    @Test
    public void getLastDayOfWeekTest()
    {
        // illegal params
        // expected result
    }

    @Test
    public void getLastDayOfMonthTest()
    {
        // illegal params
        // expected result
    }

    @Test
    public void getWeekNumofMonthTest()
    {
        // illegal params
        // expected result
    }

    @Test
    public void getWeekNumOfYearTest()
    {
        // illegal params
        // expected result
    }
    // >> Calculate
}
