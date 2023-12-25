package com.taogen.commons.regex;

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

    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(
            "(^|\\D+)\\s*(1[3-9]\\d{9})\\s*($|\\D+)", Pattern.DOTALL);

    public static final Pattern ID_CARD_PATTERN = Pattern.compile(
            "(^|\\D+)\\s*(\\d{6}(19|2[0-9])\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}(\\d|[xX]))\\s*($|\\D+)", Pattern.DOTALL);

    /**
     * Simple version: [0-9]{9,18}
     */
    public static final Pattern BANK_CARD_PATTERN = Pattern.compile(
            "(^|\\D+)\\s*([1-9](\\d{11}|\\d{15}|\\d{16}|\\d{17}|\\d{18}))\\s*($|\\D+)", Pattern.DOTALL);
}
