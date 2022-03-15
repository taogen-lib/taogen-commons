package com.taogen.commons.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Taogen
 */
public class FileUtils {
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return fileName.substring(index + 1);
    }

    public static String escapeFileName(String fileName) {
        List<Character> windowsReservedChars = Arrays.asList('\\', '/', ':', '*', '?', '"', '<', '>', '|');
        List<Character> otherChars = Arrays.asList('\r', '\n', '\t');
        if (fileName == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            if (windowsReservedChars.contains(c) || otherChars.contains(c)) {
                sb.append('-');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
