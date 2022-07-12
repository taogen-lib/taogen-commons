package com.taogen.commons.datatypes.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Taogen
 */
public class StringDesensitizationUtilsTest {
    @Test
    public void encodeMobileTest() {
        assertNull(StringDesensitizationUtils.encodeMobile(null));
        assertEquals("189****1234", StringDesensitizationUtils.encodeMobile("18912341234"));
    }
}
