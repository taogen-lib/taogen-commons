package com.taogen.commons.datatypes.string;

import org.junit.jupiter.api.Test;

import static com.taogen.commons.datatypes.string.StringMatchUtils.isEmail;
import static com.taogen.commons.datatypes.string.StringMatchUtils.isMobile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Taogen
 */
public class StringMatchUtilsTest {
    @Test
    public void isEmailTest() {
        assertFalse(isEmail(null));
        assertFalse(isEmail("123abc.com"));
        assertTrue(isEmail("hello@gmail.com"));
    }

    @Test
    public void isMobileTest() {
        assertFalse(isMobile(null));
        assertFalse(isMobile("12345678901"));
        assertTrue(isMobile("13812341234"));
    }
}
