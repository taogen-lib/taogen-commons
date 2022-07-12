package com.taogen.commons.datatypes.datetime;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static com.taogen.commons.datatypes.datetime.DateComputingUtils.dateFieldAdd;
import static com.taogen.commons.datatypes.datetime.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {
    // Convert <<

    @Test
    public void getDateFormatStrTest() {
        // illegal params
        assertNull(getDateFormatStr(null, DateFormatters.yyyy_MM_dd_1));

        // expected result
        assertEquals(DateFormatters.yyyy_MM_dd_1.format(new Date()), getDateFormatStr(new Date(), DateFormatters.yyyy_MM_dd_1));
    }

    @Test
    public void getDateByStrTest() {
        // illegal params
        assertNull(getDateByStr(null, DateFormatters.yyyy_MM_dd_1));
        assertNull(getDateByStr("2019-01-01", null));
        assertNull(getDateByStr("2019-13-01", null));
        assertNull(getDateByStr("2019-13-01", DateFormatters.yyyy_MM_dd_2));
        assertNotNull(getDateByStr("2019-13-01", DateFormatters.yyyy_MM_dd_1));

        // expected result
        assertTrue(getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1) instanceof Date);
    }

    @Test
    public void convertDateStrTest() {
        // illegal params
        assertNull(convertDateStr(null, DateFormatters.yyyy_MM_dd_1, DateFormatters.yyyy_MM_dd_2));
        assertNull(convertDateStr("2019-01-01", null, DateFormatters.yyyy_MM_dd_2));
        assertNull(convertDateStr("2019-01-01", DateFormatters.yyyy_MM_dd_1, null));
        assertNotNull(convertDateStr("2019-01-01", DateFormatters.yyyy_MM_dd_1, DateFormatters.yyyy_MM_dd_2));

        // expected result
        assertEquals("2019-01-01", convertDateStr("2019/01/01", DateFormatters.yyyy_MM_dd_2, DateFormatters.yyyy_MM_dd_1));
    }

    // >> Convert

    // ----------------------------------------------------------------

    // Calculate <<

    @Test
    public void getFieldTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getField(null, Calendar.DAY_OF_MONTH);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getField(null, 123456);
        });

        // expected result
        assertEquals(2019, getField(getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1), Calendar.YEAR));
    }

    @Test
    public void addTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            dateFieldAdd(null, Calendar.DAY_OF_MONTH, 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            dateFieldAdd(new Date(), 123456, 1);
        });

        // expected result
        assertEquals(getDateByStr("2019-01-02", DateFormatters.yyyy_MM_dd_1),
                dateFieldAdd(getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1), Calendar.DAY_OF_MONTH, 1));
    }


    @Test
    public void getFirstDayOfWeekTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getFirstDayOfWeek(null);
        });

        // expected result
        assertEquals(getDateByStr("2019-06-10", DateFormatters.yyyy_MM_dd_1), getFirstDayOfWeek(getDateByStr("2019-06-11", DateFormatters.yyyy_MM_dd_1)));
    }

    @Test
    public void getFirstDayOfMonthTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getFirstDayOfMonth(null);
        });

        // expected result
        assertEquals(getDateByStr("2019-06-01", DateFormatters.yyyy_MM_dd_1), getFirstDayOfMonth(getDateByStr("2019-06-10", DateFormatters.yyyy_MM_dd_1)));
    }

    @Test
    public void getLastDayOfWeekTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getLastDayOfWeek(null);
        });

        // expected result
        assertEquals(getDateByStr("2019-06-16", DateFormatters.yyyy_MM_dd_1), getLastDayOfWeek(getDateByStr("2019-06-10", DateFormatters.yyyy_MM_dd_1)));
    }

    @Test
    public void getLastDayOfMonthTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getLastDayOfMonth(null);
        });

        // expected result
        assertEquals(getDateByStr("2019-06-30", DateFormatters.yyyy_MM_dd_1), getLastDayOfMonth(getDateByStr("2019-06-10", DateFormatters.yyyy_MM_dd_1)));
    }

    @Test
    public void getWeekOfMonthTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getWeekOfMonth(null);
        });

        // expected result
        assertEquals(3, getWeekOfMonth(getDateByStr("2019-06-10", DateFormatters.yyyy_MM_dd_1)));
    }

    @Test
    public void getWeekNumOfYearTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getWeekOfYear(null);
        });

        // expected result
        assertEquals(24, getWeekOfYear(getDateByStr("2019-06-10", DateFormatters.yyyy_MM_dd_1)));
    }
    // >> Calculate
}
