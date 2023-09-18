package com.taogen.commons.thirdparty;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HolidayUtilsTest {

    @Test
    void isHoliday() throws ParseException, IOException {
        assertFalse(HolidayUtils.isHoliday(HolidayUtils.DATE_FORMAT_YYYY_MM_DD.parse("2020-09-01")));
        assertTrue(HolidayUtils.isHoliday(HolidayUtils.DATE_FORMAT_YYYY_MM_DD.parse("2020-10-01")));
    }
}
