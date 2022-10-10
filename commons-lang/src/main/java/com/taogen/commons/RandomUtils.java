package com.taogen.commons;

import com.taogen.commons.datatypes.string.StringUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Log4j2
public class RandomUtils {

    public static String generateRandomNumberStr(int length) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int min = 0, max = 9;
        return random.ints(length, min, max + 1)
                .mapToObj(Objects::toString)
                .collect(Collectors.joining(""));
    }

    /**
     * Random is thread safe for use by multiple threads. But if multiple threads use the same instance of Random, the same seed is shared by multiple threads. It leads to contention between multiple threads and so to performance degradation.
     * return 0 <= x < bound
     *
     * @param bound
     * @return
     */
    public static int getRandomNumber(int bound) {
//        Random random = new Random();
//        int randomNum = random.nextInt(bound);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomNum = random.nextInt(0, bound);
        log.debug("randomNum is {}", randomNum);
        return randomNum;
    }

    public static String getRandomNumberStr(int length) {
        if (length <= 0) {
            return null;
        }
        Random random = new Random();
        int bound = ((Double) Math.pow(10, length)).intValue();
        return StringUtils.expandLength(String.valueOf(random.nextInt(bound)), length, '0');
    }

    /**
     * Get Random from A-Z 0-9
     *
     * @param length
     * @return
     */
    public static String getMixedRandomStr(int length) {
        if (length <= 0) {
            return null;
        }
        int ascii_A = 65;
        int ascii_Z = 90;
        int ascii_0 = 48;
        int ascii_9 = 57;
        int size = (ascii_Z - ascii_A + 1) + (ascii_9 - ascii_0 + 1);
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int random = getRandomNumber(size);
            random = random > 9 ? random + ascii_A - 10 : random + ascii_0;
            result.append((char) (random));
        }
        return result.toString();
    }
}
