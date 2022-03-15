package com.taogen.commons.datatypes.string;

import com.taogen.commons.datatypes.string.EncodingUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class EncodingUtilsTest
{
    // verification <<

    @Test
    void isChineseTest()
    {
        assertFalse(EncodingUtils.isChinese(null));
        assertFalse(EncodingUtils.isChinese("hhh"));
        assertTrue(EncodingUtils.isChinese("你好"));
    }

    @Test
    void isEmojiTest()
    {
        assertFalse(EncodingUtils.isEmoji(null));
        assertFalse(EncodingUtils.isEmoji("hhh"));
        assertTrue(EncodingUtils.isEmoji("\ud83d\ude0ehaha"));
    }

    // >> verification

    /************************************************************/

    // Convert <<

    @Test
    void iso2Utf8Test()
    {
        System.out.println(EncodingUtils.iso2Utf8(null));
        String s1 = new String("hello".getBytes(StandardCharsets.ISO_8859_1));
        System.out.println(EncodingUtils.iso2Utf8(s1));
    }

    @Test
    void emojiConvertTest()
    {
        System.out.println("emoji convert2: " + EncodingUtils.emojiConvert(null));
        System.out.println("emoji convert: " + EncodingUtils.emojiConvert("\ud83d\ude0ehaha"));
    }

    @Test
    void emojiRecoveryTest()
    {
        System.out.println(EncodingUtils.emojiRecovery(null));
        System.out.println(EncodingUtils.emojiRecovery("%F0%9F%98%8Ehaha"));
    }

    // >> Convert
}
