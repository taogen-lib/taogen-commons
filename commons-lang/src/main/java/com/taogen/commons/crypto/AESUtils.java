package com.taogen.commons.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * The AES algorithm is an iterative, symmetric-key block cipher that supports cryptographic keys (secret keys) of 128, 192, and 256 bits to encrypt and decrypt data in blocks of 128 bits.
 * If the data to be encrypted doesn't meet the block size requirement of 128 bits, it must be padded. Padding is the process of filling up the last block to 128 bits.
 * <p>
 * Algorithms:
 * type 1
 * ECB (Electronic Code Book) it's not recommended for encryption.
 * type 2
 * CBC (Cipher Block Chaining) it need an Initialization Vector (IV)
 * CFB (Cipher FeedBack) it need an Initialization Vector (IV)
 * OFB (Output FeedBack) it need an Initialization Vector (IV)
 * type 3
 * CTR (Counter) it uses the value of a counter
 * GCM (Galois/Counter Mode) it uses the value of a counter
 * <p>
 *
 * @author Taogen
 */
public class AESUtils {

    /**
     * @param algorithm support AES/CBC/PKCS5Padding, AES/CFB/PKCS5Padding, AES/OFB/PKCS5Padding.
     *                  Java only provides PKCS#5 padding, but it is the same as PKCS#7 padding.
     *                  The difference between the PKCS#5 and PKCS#7 padding mechanisms is the block size; PKCS#5 padding is defined for 8-byte block sizes, PKCS#7 padding would work for any block size from 1 to 255 bytes.
     *                  PKCS#5 padding is a subset of PKCS#7 padding for 8 byte block sizes.
     * @param plainText
     * @param key       generate by generateAesSecretKey(String)
     * @param iv        generate by generateAesIv(String)
     * @return cipherText
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static String encryptWithIv(String algorithm,
                                       String plainText,
                                       SecretKey key,
                                       IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    /**
     * @param algorithm
     * @param cipherText
     * @param key
     * @param iv
     * @return plainText
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decryptWithIv(String algorithm, String cipherText, SecretKey key,
                                       IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    /**
     * @param sizeOfBits 128, 192, 256
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateRandomKey(int sizeOfBits) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(sizeOfBits);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static byte[] secretKeyToBytes(SecretKey secretKey) {
        return secretKey.getEncoded();
    }

    public static SecretKey bytesToSecretKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }

    public static byte[] ivToBytes(IvParameterSpec ivParameterSpec) {
        return ivParameterSpec.getIV();
    }

    public static IvParameterSpec bytesToIv(byte[] ivBytes) {
        return new IvParameterSpec(ivBytes);
    }
}
