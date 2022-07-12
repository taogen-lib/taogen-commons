package com.taogen.commons.datatypes.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taogen
 */
public class StringMatchUtils {
    public static boolean isEmail(String str) {
        String regex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        return isMatch(str, regex);
    }

    public static boolean isMobile(String str) {
        String regex = "1[3-9]\\d{9}";
        return isMatch(str, regex);
    }

    private static boolean isMatch(String str, String regex) {
        if (str == null || regex == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex); // Pattern using a pre-compiled pattern is more than 5 times faster than using String.matches method
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
