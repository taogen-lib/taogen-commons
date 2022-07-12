package com.taogen.commons.dataaccess;

/**
 * @author Taogen
 */
public class MysqlUtils {
    public static final String ON_DUPLICATE_KEY_UPDATE = " ON DUPLICATE KEY UPDATE ";

    public static String escapeCharacters(String str) {
        if (str == null) {
            return null;
        }
        str = str.replace("\'", "\\\'").replace("\"", "\\\"");
        return str;
    }
}
