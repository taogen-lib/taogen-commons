package com.taogen.commons.crypto;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashUtilsTest {

    @Test
    void md5() throws NoSuchAlgorithmException {
        String s = "taogen";
        String hashString = HashUtils.md5(s);
        System.out.println(hashString);
        assertEquals("751cb97bc325fc5a059e536b17691592", hashString);
    }

    @Test
    void sha1() throws NoSuchAlgorithmException {
        String s = "taogen";
        String hashString = HashUtils.sha1(s);
        System.out.println(hashString);
        assertEquals("67ab65d33667fa449e82cf41dbd204126323218f", hashString);
    }

    @Test
    void sha256() throws NoSuchAlgorithmException {
        String s = "taogen";
        String hashString = HashUtils.sha256(s);
        System.out.println(hashString);
        assertEquals("fe4672c0807189bce347e85748e68d720a1cab5c2d189eb8a0f45ec2d9977858", hashString);
    }

    @Test
    void sha512() throws NoSuchAlgorithmException {
        String s = "taogen";
        String hashString = HashUtils.sha512(s);
        System.out.println(hashString);
        assertEquals("616658acad46d31e4659846d9f4de54ae5825c1cf84da99d2ac6ca7464ca4c8587320f29aae56312d749262f0c45e02871d377d149d2aa0a4c4c30240e14efff", hashString);
    }
}
