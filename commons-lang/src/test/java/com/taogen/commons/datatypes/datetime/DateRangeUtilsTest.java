package com.taogen.commons.datatypes.datetime;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static com.taogen.commons.datatypes.datetime.DateComputingUtils.getDiffDays;
import static com.taogen.commons.datatypes.datetime.DateRangeUtils.getBetweenDates;
import static com.taogen.commons.datatypes.datetime.DateRangeUtils.getBetweenMonths;
import static com.taogen.commons.datatypes.datetime.DateUtils.getDateByStr;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Taogen
 */
public class DateRangeUtilsTest {
    @Test
    public void getDiffTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getDiffDays(null, new Date());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getDiffDays(new Date(), null);
        });

        // expected result
        assertEquals(3, getDiffDays(
                getDateByStr("2019-01-01 09:01:01", DateFormatters.yyyy_MM_dd_HH_mm_ss_1),
                getDateByStr("2019-01-04 09:01:01", DateFormatters.yyyy_MM_dd_HH_mm_ss_1)
        ));
        assertEquals(2, getDiffDays(
                getDateByStr("2019-01-01 09:01:01", DateFormatters.yyyy_MM_dd_HH_mm_ss_1),
                getDateByStr("2019-01-04 09:00:00", DateFormatters.yyyy_MM_dd_HH_mm_ss_1)
        ));
    }

    @Test
    public void getBetweenDatesTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenDates(null, new Date(), DateFormatters.yyyy_MM_dd_1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenDates(new Date(), null, DateFormatters.yyyy_MM_dd_1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenDates(getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1),
                    getDateByStr("2019-01-02", DateFormatters.yyyy_MM_dd_1), null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenDates(getDateByStr("2019-01-02", DateFormatters.yyyy_MM_dd_1),
                    getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1), DateFormatters.yyyy_MM_dd_1);
        });

        // expected result
        assertEquals(Arrays.asList("2019-01-01", "2019-01-02", "2019-01-03"), getBetweenDates(getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1),
                getDateByStr("2019-01-03", DateFormatters.yyyy_MM_dd_1), DateFormatters.yyyy_MM_dd_1));
    }

    @Test
    public void getBetweenMonthsTest() {
        // illegal params
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenMonths(null, new Date(), DateFormatters.yyyy_MM_dd_1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenMonths(new Date(), null, DateFormatters.yyyy_MM_dd_1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenMonths(getDateByStr("2019-01-01", DateFormatters.yyyy_MM_dd_1), new Date(), null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            getBetweenMonths(getDateByStr("2019-02", DateFormatters.yyyy_MM_1), getDateByStr("2019-01", DateFormatters.yyyy_MM_1), DateFormatters.yyyy_MM_1);
        });

        // expected result
        assertEquals(Arrays.asList(DateFormatters.yyyy_MM_1.format(new Date())), getBetweenMonths(new Date(), new Date(), DateFormatters.yyyy_MM_1));
        assertEquals(Arrays.asList("2019-01", "2019-02"), getBetweenMonths(getDateByStr("2019-01", DateFormatters.yyyy_MM_1), getDateByStr("2019-02", DateFormatters.yyyy_MM_1), DateFormatters.yyyy_MM_1));
    }
}
