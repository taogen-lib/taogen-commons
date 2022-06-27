package com.taogen.commons.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Taogen
 */
public class RSAUtils {


    public static RSAKeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();
        Key pub = keyPair.getPublic();
        Key pvt = keyPair.getPrivate();
        String publicKey = Base64.getEncoder().encodeToString(pub.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(pvt.getEncoded());
        return new RSAKeyPair(pub, publicKey, pvt, privateKey);
    }

    public static String decrypt(String privateKeyBase64String, String cipherText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64String.getBytes()));
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(bytes);
    }

    public static String encrypt(String publicKeyBase64String, String plainText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64String.getBytes()));
        PublicKey publicKey = keyFactory.generatePublic(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes());
        return new String(Base64.getEncoder().encode(bytes));
    }


    static class RSAKeyPair {
        private Key publicKey;
        private Key privateKey;
        private String publicKeyBase64;
        private String privateKeyBase64;

        public RSAKeyPair() {
        }

        public RSAKeyPair(Key publicKey,
                          String publicKeyBase64,
                          Key privateKey,
                          String privateKeyBase64) {
            this.publicKey = publicKey;
            this.publicKeyBase64 = publicKeyBase64;
            this.privateKey = privateKey;
            this.privateKeyBase64 = privateKeyBase64;
        }

        public Key getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(Key publicKey) {
            this.publicKey = publicKey;
        }

        public Key getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(Key privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKeyBase64() {
            return publicKeyBase64;
        }

        public void setPublicKeyBase64(String publicKeyBase64) {
            this.publicKeyBase64 = publicKeyBase64;
        }

        public String getPrivateKeyBase64() {
            return privateKeyBase64;
        }

        public void setPrivateKeyBase64(String privateKeyBase64) {
            this.privateKeyBase64 = privateKeyBase64;
        }
    }

}
