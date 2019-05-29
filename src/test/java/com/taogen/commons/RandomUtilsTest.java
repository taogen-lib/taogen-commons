package com.taogen.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomUtilsTest {

    @Test 
    public void getRandomNumberTest()
    {
        int bound1 = 20;
        assertTrue(RandomUtils.getRandomNumber(bound1) < bound1 && RandomUtils.getRandomNumber(bound1) > 0);
    }

    @Test
    public void getRandomNumberStrTest()
    {
        assertNull(RandomUtils.getRandomNumberStr(0));
        assertNull(RandomUtils.getRandomNumberStr(-1));
        assertTrue(RandomUtils.getRandomNumberStr(10).matches("^[0-9]{10}$"));
    }

    @Test
    public void getMixedRandomStrTest()
    {
        assertNull(RandomUtils.getMixedRandomStr(0));
        assertNull(RandomUtils.getMixedRandomStr(-1));
        assertTrue(RandomUtils.getMixedRandomStr(10).matches("^[0-9A-Z]{10}$"));
    }

}
