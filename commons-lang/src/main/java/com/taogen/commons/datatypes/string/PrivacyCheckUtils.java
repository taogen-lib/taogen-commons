package com.taogen.commons.datatypes.string;

import com.taogen.commons.regex.RegexPatterns;
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

    /**
     * Get mobile, id card, bank card number's positions
     *
     * @param text
     * @return position start from 0
     */
    public static CheckResult getCheckResult(String text) {
        CheckResult checkResult = new CheckResult();
        int group = 2;
        checkResult.setPhone(getMatchPositions(text, RegexPatterns.PHONE_NUMBER_PATTERN, group));
        checkResult.setIdCard(getMatchPositions(text, RegexPatterns.ID_CARD_PATTERN, group));
        checkResult.setBankCard(getMatchPositions(text, RegexPatterns.BANK_CARD_PATTERN, group));
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
