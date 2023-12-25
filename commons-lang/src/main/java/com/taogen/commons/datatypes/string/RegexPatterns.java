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

    public static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4E00-\\u9FA5]");

    public static final Pattern EMOJI_PATTERN = Pattern.compile("([\\x{10000}-\\x{10ffff}\ud800-\udfff])");
}
