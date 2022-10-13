package com.taogen.commons.datatypes.string;

import java.util.regex.Pattern;

/**
 * @author Taogen
 */
public class RegexPatterns {
    /**
     * password strength criteria
     * - 12 characters length
     * - at least one letter
     * - at least one number
     */
    public static final Pattern PASSWORD_STRENGTH_1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d){12}");

}
