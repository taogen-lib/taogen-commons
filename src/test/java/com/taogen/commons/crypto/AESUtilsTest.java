package com.taogen.commons.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESUtilsTest {


    @Test
    void encryptWithVI() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String input = "taogen";
        SecretKey key = AESUtils.generateRandomKey(128);
        IvParameterSpec ivParameterSpec = AESUtils.generateIv();
        System.out.println("key bytes: " + Arrays.toString(key.getEncoded()));
        System.out.println("key hex: " + CryptoUtils.bytesToHexString(key.getEncoded()));
        System.out.println("iv hex: " + CryptoUtils.bytesToHexString(ivParameterSpec.getIV()));
        System.out.println("key base64: " + CryptoUtils.bytesToBase64(key.getEncoded()));
        System.out.println("iv base64: " + CryptoUtils.bytesToBase64(ivParameterSpec.getIV()));
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = AESUtils.encryptWithIv(algorithm, input, key, ivParameterSpec);
        System.out.println("cipherText: " + cipherText);
        key = AESUtils.bytesToSecretKey(key.getEncoded());
        ivParameterSpec = AESUtils.bytesToIv(ivParameterSpec.getIV());
        String plainText = AESUtils.decryptWithIv(algorithm, cipherText, key, ivParameterSpec);
        System.out.println(plainText);
        assertEquals(input, plainText);
    }

}
