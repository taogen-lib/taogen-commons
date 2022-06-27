package com.taogen.commons.crypto;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author Taogen
 */
public class CryptoUtils {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static void printBytes(byte[] bytes) {
        System.out.println(Arrays.toString(bytes));
    }

    /**
     * faster solution
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * no external libs, no digits constants
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString2(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * In Java 8 and earlier, JAXB was part of the Java standard library.
     * It was deprecated with Java 9 and removed with Java 11, as part of an effort to move all Java EE packages into their own libraries.
     * Now, javax.xml.bind doesn't exist, and if you want to use JAXB, which contains DatatypeConverter, you'll need to install the JAXB API and JAXB Runtime from Maven.
     *
     * @param bytes
     * @return
     */
//    public static String bytesToHexString3(byte[] bytes) {
//        return javax.xml.bind.DatatypeConverter.printHexBinary(bytes);
//    }

    /**
     * Note that since this handles numbers not arbitrary byte-strings it will omit leading zeros - this may or may not be what you want (e.g. 000AE3 vs 0AE3 for a 3 byte input). This is also very slow.
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString4(byte[] bytes) {
        return new BigInteger(1, bytes).toString(16);
    }

    public static byte[] hexStringToBytes(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }
}
