package com.taogen.commons.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Taogen
 */
public class EncodeUtils {
    public static String toBase64(String source) {
        return Base64.getEncoder().encodeToString(
                source.getBytes(StandardCharsets.UTF_8));
    }

    public static String fromBase64(String source) {
        return new String(Base64.getDecoder().decode(source),
                StandardCharsets.UTF_8);
    }

    public static String urlEncode(String source) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(source, StandardCharsets.UTF_8.name());
    }

    public static String urlDecode(String source) throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(source, StandardCharsets.UTF_8.name());
    }
}
