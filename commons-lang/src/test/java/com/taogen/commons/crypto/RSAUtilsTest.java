package com.taogen.commons.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSAUtilsTest {

    @Test
    void encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        RSAUtils.RSAKeyPair keyPair = RSAUtils.getKeyPair();
        String input = "taogen";
        String cipherText = RSAUtils.encrypt(keyPair.getPublicKeyBase64(), input);
        System.out.println(cipherText);
        String plainText = RSAUtils.decrypt(keyPair.getPrivateKeyBase64(), cipherText);
        System.out.println(plainText);
        assertEquals(input, plainText);
    }
}
