package com.taogen.commons.datatypes.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {
    // convert <<

    @Test
    public void str2IntTest() {
        assertEquals(-1, StringUtils.str2Int(null, -1));
        assertEquals(12, StringUtils.str2Int("12", 0));
        assertEquals(0, StringUtils.str2Int("a", 0));
    }

    @Test
    public void str2LongTest() {
        assertEquals(12L, StringUtils.str2Long("12", 0));
        assertEquals(0, StringUtils.str2Long("a", 0));
    }

    @Test
    public void str2DoubleTest() {
        assertEquals(12.223, StringUtils.str2Double("12.223", 0.0), 0.0001); // three bits precision
        assertEquals(0, StringUtils.str2Double("a", 0.0), 0.0001);
    }

    @Test
    public void str2BooleanTest() {
        assertFalse(StringUtils.str2Boolean(null));
        assertFalse(StringUtils.str2Boolean("a"));
        assertTrue(StringUtils.str2Boolean("true")); // three bits precision
    }

    // >> convert


    /************************************************************/


    // string verification <<
    @Test
    public void isEmpty() {
        assertFalse(StringUtils.isEmpty("abc"));
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
    }


    // >> string verification


    /************************************************************/


    // string handle <<
    @Test
    public void jointStringTest() {
        assertEquals("a", StringUtils.jointString("a", null));
        assertEquals("a1b2", StringUtils.jointString("a1", "b2"));
    }

    @Test
    public void toNotNullTest() {
        assertEquals("", StringUtils.toNotNull(null));
        assertEquals("hello", StringUtils.toNotNull("hello"));
    }

    @Test
    public void expandLengthTest() {
        assertNull(StringUtils.expandLength(null, 0, ' '));
        assertNull(StringUtils.expandLength("abc", 1, ' '));
        assertEquals("0abc", StringUtils.expandLength("abc", 4, '0'));
    }
    // >> string handle


    /************************************************************/


    @Test
    public void countOccurrencesOfSubstring() {
        assertEquals(0, StringUtils.countOccurrencesOfSubstring(null, null));
        assertEquals(0, StringUtils.countOccurrencesOfSubstring(null, ""));
        assertEquals(0, StringUtils.countOccurrencesOfSubstring("", null));
        assertEquals(0, StringUtils.countOccurrencesOfSubstring("", ""));
        assertEquals(1, StringUtils.countOccurrencesOfSubstring("abc", "a"));
        assertEquals(1, StringUtils.countOccurrencesOfSubstring("abc", "b"));
        assertEquals(1, StringUtils.countOccurrencesOfSubstring("abc", "c"));
        assertEquals(2, StringUtils.countOccurrencesOfSubstring("abcabc", "a"));
        assertEquals(2, StringUtils.countOccurrencesOfSubstring("abcabc", "bc"));
    }
}
