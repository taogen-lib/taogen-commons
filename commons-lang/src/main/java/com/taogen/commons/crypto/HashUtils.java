package com.taogen.commons.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Taogen
 */
public class HashUtils {
    public static String md5(String source) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }

    public static String sha1(String source) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }

    public static String sha256(String source) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }

    public static String sha512(String source) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.reset();
        messageDigest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return CryptoUtils.bytesToHexString(digest);
    }
}
