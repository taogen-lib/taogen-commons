package com.taogen.commons;

import java.util.Random;

public class RandomUtils
{

    public static int getRandomNumber(int bound)
    {
        Random random = new Random();
        return random.nextInt(bound);
    }

    public static String getRandomNumberStr(int length)
    {
        if (length <= 0)
        {
            return null;
        }
        Random random = new Random();
        int bound = ((Double)Math.pow(10, length)).intValue();
        return StringUtils.expandLength(String.valueOf(random.nextInt(bound)), length,'0');
    }

    /**
     * Get Random from A-Z 0-9
     * @param length
     * @return
     */
    public static String getMixedRandomStr(int length)
    {
        if (length <= 0)
        {
            return null;
        }
        int ascii_A = 65;
        int ascii_Z = 90;
        int ascii_0 = 48;
        int ascii_9 = 57;
        int size = (ascii_Z - ascii_A + 1) + (ascii_9 - ascii_0 + 1);
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++)
        {
            int random = getRandomNumber(size);
            random = random > 9 ? random + ascii_A - 10 : random + ascii_0;
            result.append((char)(random));
        }
        return result.toString();
    }
}
