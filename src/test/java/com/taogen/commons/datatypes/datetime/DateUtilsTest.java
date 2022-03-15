package com.taogen.commons.datatypes.datetime;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.taogen.commons.datatypes.datetime.DateUtils.*;
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
        assertThrows(IllegalArgumentException.class, () -> { getField(null, 123456); });

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
        assertThrows(IllegalArgumentException.class, () ->{getDiffDays(null, new Date());});
        assertThrows(IllegalArgumentException.class, () ->{getDiffDays(new Date(), null);});

        // expected result
        assertEquals(3, getDiffDays(
                getDateByStr("2019-01-01 09:01:01", FORMAT_YYYY_MM_DD_HH_MM_SS),
                getDateByStr("2019-01-04 09:01:01", FORMAT_YYYY_MM_DD_HH_MM_SS)
        ));
        assertEquals(2, getDiffDays(
                getDateByStr("2019-01-01 09:01:01", FORMAT_YYYY_MM_DD_HH_MM_SS),
                getDateByStr("2019-01-04 09:00:00", FORMAT_YYYY_MM_DD_HH_MM_SS)
        ));
    }

    @Test
    public void getBetweenDatesTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{getBetweenDates(null, new Date(), FORMAT_YYYY_MM_DD_1);});
        assertThrows(IllegalArgumentException.class, () ->{getBetweenDates(new Date(), null, FORMAT_YYYY_MM_DD_1);});
        assertThrows(IllegalArgumentException.class, () ->{getBetweenDates(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-02", FORMAT_YYYY_MM_DD_1), null);});
        assertThrows(IllegalArgumentException.class, () ->{getBetweenDates(getDateByStr("2019-01-02", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1), FORMAT_YYYY_MM_DD_1);});

        // expected result
        assertEquals(Arrays.asList("2019-01-01", "2019-01-02", "2019-01-03"), getBetweenDates(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1),
                getDateByStr("2019-01-03", FORMAT_YYYY_MM_DD_1),FORMAT_YYYY_MM_DD_1));
    }

    @Test
    public void getBetweenMonthsTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ getBetweenMonths(null, new Date(), FORMAT_YYYY_MM_DD_1); });
        assertThrows(IllegalArgumentException.class, () ->{ getBetweenMonths(new Date(), null, FORMAT_YYYY_MM_DD_1); });
        assertThrows(IllegalArgumentException.class, () ->{ getBetweenMonths(getDateByStr("2019-01-01", FORMAT_YYYY_MM_DD_1), new Date(), null); });
        assertThrows(IllegalArgumentException.class, () ->{ getBetweenMonths(getDateByStr("2019-02", FORMAT_YYYYMM_1), getDateByStr("2019-01", FORMAT_YYYYMM_1), FORMAT_YYYYMM_1); });

        // expected result
        assertEquals(Arrays.asList("2019-06"), getBetweenMonths(new Date(), new Date(), FORMAT_YYYYMM_1));
        assertEquals(Arrays.asList("2019-01", "2019-02"), getBetweenMonths(getDateByStr("2019-01", FORMAT_YYYYMM_1), getDateByStr("2019-02", FORMAT_YYYYMM_1), FORMAT_YYYYMM_1));
    }

    @Test
    public void getFirstDayOfWeekTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ getFirstDayOfWeek(null); });

        // expected result
        assertEquals(getDateByStr("2019-06-10",FORMAT_YYYY_MM_DD_1), getFirstDayOfWeek(getDateByStr("2019-06-11", FORMAT_YYYY_MM_DD_1)));
    }

    @Test
    public void getFirstDayOfMonthTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ getFirstDayOfMonth(null); });

        // expected result
        assertEquals(getDateByStr("2019-06-01",FORMAT_YYYY_MM_DD_1), getFirstDayOfMonth(getDateByStr("2019-06-10",FORMAT_YYYY_MM_DD_1)));
    }

    @Test
    public void getLastDayOfWeekTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> { getLastDayOfWeek(null); });

        // expected result
        assertEquals(getDateByStr("2019-06-16", FORMAT_YYYY_MM_DD_1), getLastDayOfWeek(getDateByStr("2019-06-10", FORMAT_YYYY_MM_DD_1)));
    }

    @Test
    public void getLastDayOfMonthTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ getLastDayOfMonth(null); });

        // expected result
        assertEquals(getDateByStr("2019-06-30", FORMAT_YYYY_MM_DD_1), getLastDayOfMonth(getDateByStr("2019-06-10", FORMAT_YYYY_MM_DD_1)));
    }

    @Test
    public void getWeekOfMonthTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ getWeekOfMonth(null); });

        // expected result
        assertEquals(3, getWeekOfMonth(getDateByStr("2019-06-10", FORMAT_YYYY_MM_DD_1)));
    }

    @Test
    public void getWeekNumOfYearTest()
    {
        // illegal params
        assertThrows(IllegalArgumentException.class, () ->{ getWeekOfYear(null); });

        // expected result
        assertEquals(24, getWeekOfYear(getDateByStr("2019-06-10", FORMAT_YYYY_MM_DD_1)));
    }
    // >> Calculate
}
