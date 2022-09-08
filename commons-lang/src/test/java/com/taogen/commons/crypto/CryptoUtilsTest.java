package com.taogen.commons.crypto;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoUtilsTest {

    @Test
    void byteArrayToHexString() {
        byte[] bytes = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10};
        String hexString = CryptoUtils.bytesToHexString(bytes);
        assertEquals("0102030405060708090a0b0c0d0e0f10", hexString);
    }

    @Test
    void byteArrayToHexString2() {
        byte[] bytes = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10};
        String hexString = CryptoUtils.bytesToHexString2(bytes);
        assertEquals("0102030405060708090a0b0c0d0e0f10", hexString);
    }

    @Test
    void hexStringToByteArray() {
        String hexString = "0102030405060708090a0b0c0d0e0f10";
        byte[] bytes = CryptoUtils.hexStringToBytes(hexString);
        assertArrayEquals(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10}, bytes);
    }

    @Test
    void byteArrayToBase64String() {
        byte[] bytes = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10};
        String base64String = CryptoUtils.bytesToBase64(bytes);
        String expect = Base64.getEncoder().encodeToString(bytes);
        assertEquals(expect, base64String);
    }

    @Test
    void base64StringToByteArray() {
        byte[] bytes = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10};
        String base64String = Base64.getEncoder().encodeToString(bytes);
        byte[] byteArray = CryptoUtils.base64ToBytes(base64String);
        byte[] expect = Base64.getDecoder().decode(base64String);
        assertArrayEquals(expect, byteArray);
    }
}
