package com.taogen.commons;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nUtilsTest {
    @Test
    public void getMessageTest() throws URISyntaxException {
        assertEquals("test", I18nUtils.getMessage("test", null));
        assertEquals("test", I18nUtils.getMessage("test", new Locale("en")));
        assertEquals("测试", I18nUtils.getMessage("test", new Locale("zh", "CN")));
    }
}
