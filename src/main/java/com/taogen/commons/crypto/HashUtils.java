package com.taogen.commons.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Taogen
 */
public class HashUtils {
    public static String md5(String source) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }

    public static String sha1(String source) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }

    public static String sha256(String source) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }

    public static String sha512(String source) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }
}
