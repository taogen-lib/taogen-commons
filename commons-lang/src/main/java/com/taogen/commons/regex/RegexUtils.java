package com.taogen.commons.regex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author taogen
 */
public class RegexUtils {
    public static String replaceMatchGroups(String source, Pattern pattern, Map<Integer, String> groupToReplacement) {
        Matcher matcher = pattern.matcher(source);
        StringBuilder result = new StringBuilder(source);
        int adjust = 0;
        while (matcher.find()) {
            for (Map.Entry<Integer, String> entry : groupToReplacement.entrySet()) {
                int groupToReplace = entry.getKey();
                String replacement = entry.getValue();
                int start = matcher.start(groupToReplace);
                int end = matcher.end(groupToReplace);
                result.replace(start + adjust, end + adjust, replacement);
                adjust += replacement.length() - (end - start);
            }
        }
        return result.toString();
    }
}
