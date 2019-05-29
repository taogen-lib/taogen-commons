package com.taogen.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodingUtils
{

    // verification <<

    public static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4E00-\\u9FA5]");
    public static final Pattern EMOJI_PATTERN = Pattern.compile("([\\x{10000}-\\x{10ffff}\ud800-\udfff])");

    public static boolean isChinese(String str)
    {
        if (str == null)
        {
            return false;
        }
        Matcher matcher = CHINESE_PATTERN.matcher(str);
        return matcher.find();
    }

    public static boolean isEmoji(String str)
    {
        if (str == null)
        {
            return false;
        }
        Matcher matcher = EMOJI_PATTERN.matcher(str);
        return matcher.find();
    }

    // >> verification

    //----------------------------------------------------------------

    // Convert <<

    public static String iso2Utf8(String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return str;
        }
        else
        {
            try
            {
                return new String(str.getBytes(StandardCharsets.ISO_8859_1.toString()), StandardCharsets.UTF_8.toString());
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return str;
            }
        }
    }

    public static String emojiConvert (String str)
    {
        if (str == null)
        {
            return null;
        }
        Matcher matcher = EMOJI_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find())
        {
            try
            {
//				matcher.appendReplacement(sb, "[["
//						+ URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
                matcher.appendReplacement(sb, URLEncoder.encode(matcher.group(1), StandardCharsets.UTF_8.toString()));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        matcher.appendTail(sb);
        return sb.toString();

    }

    public static String emojiRecovery (String str)
    {
        String output = null;
        if (str != null)
        {
            try
            {
                output = URLDecoder.decode(str, StandardCharsets.UTF_8.toString());
            }
            catch (UnsupportedEncodingException e)
            {
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



    // >> Convert


}
