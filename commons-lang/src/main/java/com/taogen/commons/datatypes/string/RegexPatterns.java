package com.taogen.commons.datatypes.string;

import java.util.regex.Pattern;

/**
 * @author Taogen
 */
public class RegexPatterns {
    /**
     * Positive lookahead assertions: (?=(.*RULE){MIN_OCCURRENCES,}) or (?=.*RULE)
     * password strength criteria
     * - 12 characters length --- .{12}
     * - at least one letter --- (?=.*[a-zA-Z])
     * - at least one number -- (?=.*\\d)
     */
    public static final Pattern PASSWORD_STRENGTH_1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{12}$");
}
