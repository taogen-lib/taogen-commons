package com.taogen.commons.thirdparty;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class HolidayUtilsTest {

    @Test
    void getHolidayOfYear() throws IOException {
        int thisYear = Year.now().getValue();
        log.debug("thisYear: {}", thisYear);
        String holidayOfYear = HolidayUtils.getHolidayOfYear(thisYear);
        log.debug("holidayOfYear: {}", holidayOfYear);
        assertNotNull(holidayOfYear);
        assertTrue(holidayOfYear.contains(thisYear + "-01-01"));
        assertTrue(holidayOfYear.contains(thisYear + "-10-01"));
    }
    @Test
    void isHoliday() throws ParseException, IOException {
        assertFalse(HolidayUtils.isHoliday(HolidayUtils.DATE_FORMAT_YYYY_MM_DD.parse("2020-09-01")));
        assertTrue(HolidayUtils.isHoliday(HolidayUtils.DATE_FORMAT_YYYY_MM_DD.parse("2020-10-01")));
    }
}
