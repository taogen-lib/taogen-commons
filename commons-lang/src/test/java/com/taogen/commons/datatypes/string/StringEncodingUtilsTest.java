package com.taogen.commons.datatypes.string;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringEncodingUtilsTest {
    // verification <<

    @Test
    void isChineseTest() {
        assertFalse(StringEncodingUtils.isChinese(null));
        assertFalse(StringEncodingUtils.isChinese("hhh"));
        assertTrue(StringEncodingUtils.isChinese("你好"));
    }

    @Test
    void isEmojiTest() {
        assertFalse(StringEncodingUtils.isEmoji(null));
        assertFalse(StringEncodingUtils.isEmoji("hhh"));
        assertTrue(StringEncodingUtils.isEmoji("\ud83d\ude0ehaha"));
    }

    // >> verification

    /************************************************************/

    // Convert <<
    @Test
    void iso2Utf8Test() {
        System.out.println(StringEncodingUtils.iso2Utf8(null));
        String s1 = new String("hello".getBytes(StandardCharsets.ISO_8859_1));
        System.out.println(StringEncodingUtils.iso2Utf8(s1));
    }

    @Test
    void emojiConvertTest() {
        System.out.println("emoji convert2: " + StringEncodingUtils.emojiConvert(null));
        System.out.println("emoji convert: " + StringEncodingUtils.emojiConvert("\ud83d\ude0ehaha"));
    }

    @Test
    void emojiRecoveryTest() {
        System.out.println(StringEncodingUtils.emojiRecovery(null));
        System.out.println(StringEncodingUtils.emojiRecovery("%F0%9F%98%8Ehaha"));
    }

    // >> Convert
}
