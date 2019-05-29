package com.taogen.commons;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class I18nUtilsTest
{
    @Test
    public void getMessageTest()
    {
        assertEquals("test", I18nUtils.getMessage("test", null));
        assertEquals("test", I18nUtils.getMessage("test", new Locale("en")));
        assertEquals("测试", I18nUtils.getMessage("test", new Locale("zh", "CN")));
    }
}
