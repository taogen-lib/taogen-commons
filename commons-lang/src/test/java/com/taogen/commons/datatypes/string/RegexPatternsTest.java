package com.taogen.commons.datatypes.string;

import org.junit.jupiter.api.Test;

import static com.taogen.commons.regex.RegexPatterns.PASSWORD_STRENGTH_1;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexPatternsTest {
    @Test
    void PASSWORD_STRENGTH_1() {
        assertFalse(PASSWORD_STRENGTH_1.matcher("12345678901").find());
        assertFalse(PASSWORD_STRENGTH_1.matcher("123456789012").find());
        assertFalse(PASSWORD_STRENGTH_1.matcher("1234567890123").find());
        assertFalse(PASSWORD_STRENGTH_1.matcher("abcdefghijk").find());
        assertFalse(PASSWORD_STRENGTH_1.matcher("abcdefghijkl").find());
        assertFalse(PASSWORD_STRENGTH_1.matcher("abcdefghijklm").find());
        assertTrue(PASSWORD_STRENGTH_1.matcher("12345678901a").find());
        assertTrue(PASSWORD_STRENGTH_1.matcher("123456b8901a").find());
        assertTrue(PASSWORD_STRENGTH_1.matcher("abcdefghijk0").find());
        assertTrue(PASSWORD_STRENGTH_1.matcher("abcdef1hijk0").find());
    }

}
