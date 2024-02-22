package com.taogen.commons.datatypes.string;

import com.taogen.commons.regex.RegexPatterns;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public class StringEncodingUtils {

    // verification <<


    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        Matcher matcher = RegexPatterns.CHINESE_PATTERN.matcher(str);
        return matcher.find();
    }

    public static boolean isEmoji(String str) {
        if (str == null) {
            return false;
        }
        Matcher matcher = RegexPatterns.EMOJI_PATTERN.matcher(str);
        return matcher.find();
    }

    // >> verification

    //----------------------------------------------------------------

    // Convert <<

    public static String iso2Utf8(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        } else {
            try {
                return new String(str.getBytes(StandardCharsets.ISO_8859_1.toString()), StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return str;
            }
        }
    }

    public static String emojiConvert(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = RegexPatterns.EMOJI_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
//				matcher.appendReplacement(sb, "[["
//						+ URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
                matcher.appendReplacement(sb, URLEncoder.encode(matcher.group(), StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        matcher.appendTail(sb);
        return sb.toString();

    }

    public static String emojiRecovery(String str) {
        String output = null;
        if (str != null) {
            try {
                output = URLDecoder.decode(str, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

//    public static String emojiRecovery(String str)
//    {
//		String patternString = "\\[\\[(.*?)\\]\\]";
//
//		Pattern pattern = Pattern.compile(patternString);
//		Matcher matcher = pattern.matcher(str);
//
//		StringBuffer sb = new StringBuffer();
//		while (matcher.find())
//		{
//
//			try
//			{
//				matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
//			}
//			catch (UnsupportedEncodingException e)
//			{
//				e.printStackTrace();
//			}
//
//		}
//		matcher.appendTail(sb);
//		return sb.toString();
//    }

    public static String filterEmoji(String source) {
        if (source != null) {
//            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
//                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = RegexPatterns.EMOJI_PATTERN.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("");
                return source;
            }
            return source;
        }
        return source;
    }

    // >> Convert


}
