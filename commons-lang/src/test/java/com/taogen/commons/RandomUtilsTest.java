package com.taogen.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomUtilsTest {

    @Test
    public void getRandomNumberTest() {
        int bound = 1;
        int randomNumber = RandomUtils.getRandomNumber(bound);
        assertTrue(randomNumber >= 0 && randomNumber < bound);
        bound = 2;
        int randomNumber2 = RandomUtils.getRandomNumber(bound);
        assertTrue(randomNumber2 >= 0 && randomNumber2 < bound);
    }

    @Test
    public void getRandomNumberStrTest() {
        assertNull(RandomUtils.getRandomNumberStr(0));
        assertNull(RandomUtils.getRandomNumberStr(-1));
        assertTrue(RandomUtils.getRandomNumberStr(10).matches("^[0-9]{10}$"));
    }

    @Test
    public void getMixedRandomStrTest() {
        assertNull(RandomUtils.getMixedRandomStr(0));
        assertNull(RandomUtils.getMixedRandomStr(-1));
        assertTrue(RandomUtils.getMixedRandomStr(10).matches("^[0-9A-Z]{10}$"));
    }

}
