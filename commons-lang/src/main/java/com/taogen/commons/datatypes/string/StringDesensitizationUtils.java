package com.taogen.commons.datatypes.string;

/**
 * @author Taogen
 */
public class StringDesensitizationUtils {
    public static String encodeMobile(String str)
    {
        if (StringMatchUtils.isMobile(str))
        {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, 3));
            sb.append("****");
            sb.append(str.substring(7));
            str = sb.toString();
        }
        return str;
    }
}
