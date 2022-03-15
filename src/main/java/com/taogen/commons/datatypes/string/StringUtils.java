package com.taogen.commons.datatypes.string;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{

    // Convert <<

//    public static int str2Int(String s1, int def)
//    {
//        int result = def;
//        try
//        {
//            result = Integer.parseInt(str);
//        }
//        catch (NumberFormatException e)
//        {
//            e.printStackTrace();
//            result = def;
//        }
//        return result;
//    }

    public static int str2Int(String s1, int def)
    {
        return (Integer) parseToPrimitiveType(s1, Integer.class, def);
    }

    public static long str2Long(String s1, long def)
    {
        return (Long) parseToPrimitiveType(s1, Long.class, def);
    }

    public static double str2Double(String s1, double def)
    {
        return (Double) parseToPrimitiveType(s1, Double.class, def);
    }

    /**
     * "true" return true, other string and null return false
     */
    public static Boolean str2Boolean(String s1)
    {
        return (Boolean) parseToPrimitiveType(s1, Boolean.class, null);
    }

    /**
     * @param str
     * @param cls Class of Primitive data type. Like Integer, Double, Boolean etc.
     * @param def when occur exception, return the default value
     * @return
     */
    public static Object parseToPrimitiveType(String str, Class cls, Object def)
    {
        Object result = null;
        String methodName = "";

        if (cls == Integer.class)
        {
            methodName = "parseInt";
        }
        else
        {
            methodName = "parse" + cls.getSimpleName();
        }

        try
        {
            result = cls.getMethod(methodName, String.class).invoke(null, str);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException  e)
        {
            System.out.println(e);
        }
        // System.out.println("result: "+ result);
        if (result == null)
        {
            result = def;
        }
        return result;
    }

    // >> Convert


    //-------------------------------------------------------------------


    // string verification <<

    public static boolean isEmpty(String str)
    {
        return (str == null || str.length() == 0) ? true : false;
    }

    public static boolean isEmail(String str)
    {
        String regex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        return isMatch(str, regex);
    }

    public static boolean isMobile(String str)
    {
        String regex = "1[3-9]\\d{9}";
        return isMatch(str, regex);
    }

    private static boolean isMatch(String str, String regex)
    {
        if (str == null || regex == null)
        {
            return false;
        }
        Pattern pattern = Pattern.compile(regex); // Pattern using a pre-compiled pattern is more than 5 times faster than using String.matches method
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    // >> string verification


    // -------------------------------------------------------------


    // string handle <<

    public static String encodeMobile(String str)
    {
        if (isMobile(str))
        {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, 3));
            sb.append("****");
            sb.append(str.substring(7));
            str = sb.toString();
        }
        return str;
    }

    public static String jointString(String... strs)
    {
        if (strs == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strs)
        {
            if (s != null)
            {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static String toNotNull(String str)
    {
        return str == null ? "" : str;
    }

    public static String expandLength(String str, int len, char fillChar)
    {
        if (len < 0 || str == null || len < str.length())
        {
            return null;
        }
        str = String.format("%"+len+"s", str).replace(' ', fillChar);
        //System.out.println("str:" + str);
        return str;
    }
    // >> string handle


    // ------------------------------------------------------------------------------

    public static int countOccurrencesOfSubstring(String str, String sub) {
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
