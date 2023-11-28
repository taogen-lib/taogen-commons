package com.taogen.commons.datatypes.string;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taogen
 */
@Log4j2
public class PrivacyCheckUtils {
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(
            "(^|\\D+)\\s*(1[3-9]\\d{9})\\s*($|\\D+)", Pattern.DOTALL);
    public static final Pattern ID_CARD_PATTERN = Pattern.compile(
            "(^|\\D+)\\s*(\\d{6}(19|2[0-9])\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}(\\d|[xX]))\\s*($|\\D+)", Pattern.DOTALL);
    /**
     * Simple version: [0-9]{9,18}
     */
    public static final Pattern BANK_CARD_PATTERN = Pattern.compile(
            "(^|\\D+)\\s*([1-9](\\d{11}|\\d{15}|\\d{16}|\\d{17}|\\d{18}))\\s*($|\\D+)", Pattern.DOTALL);

    /**
     * Get mobile, id card, bank card number's positions
     *
     * @param text
     * @return position start from 0
     */
    public static CheckResult getCheckResult(String text) {
        CheckResult checkResult = new CheckResult();
        int group = 2;
        checkResult.setPhone(getMatchPositions(text, PHONE_NUMBER_PATTERN, group));
        checkResult.setIdCard(getMatchPositions(text, ID_CARD_PATTERN, group));
        checkResult.setBankCard(getMatchPositions(text, BANK_CARD_PATTERN, group));
        return checkResult;
    }

    private static List<List<Integer>> getMatchPositions(String text, Pattern pattern, Integer group) {
        log.debug("pattern: {}", pattern.pattern());
        Matcher matcher = pattern.matcher(text);
        List<List<Integer>> matchPositions = new ArrayList<>();
        while (matcher.find()) {
            log.debug(matcher.group(group));
            int start = matcher.start(group);
            int end = matcher.end(group);
            matchPositions.add(Arrays.asList(start, end));
        }
        return matchPositions;
    }

    @Data
    public static class CheckResult {
        private List<List<Integer>> phone;
        private List<List<Integer>> idCard;
        private List<List<Integer>> bankCard;
    }

}
