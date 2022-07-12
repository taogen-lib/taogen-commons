package com.taogen.commons.io;

import com.taogen.commons.datatypes.datetime.DateFormatters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Taogen
 */
public class FileUtils {
    public static final List<String> BROWSER_PREVIEW_FILE_SUFFIXES = Arrays.asList(".pdf", ".png", ".jpg", ".jpeg", ".gif", ".txt");

    /**
     * For example: text.txt -> text_2022-06-23_16-01-01.txt, text -> text_2022-06-23_16-01-01
     *
     * @param fileName
     * @return
     */
    public static String appendDateTimeToFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return new StringBuilder()
                    .append(fileName, 0, index)
                    .append("_")
                    .append(DateFormatters.yyyy_MM_dd_HH_mm_ss_SSS_2.format(new Date()))
                    .append(fileName.substring(index))
                    .toString();
        } else {
            return new StringBuilder()
                    .append(fileName)
                    .append("_")
                    .append(DateFormatters.yyyy_MM_dd_HH_mm_ss_SSS_2.format(new Date()))
                    .toString();
        }
    }

    public static boolean doesFileExist(String dirOrFilePath) throws FileNotFoundException {
        File file = new File(dirOrFilePath);
        return file.exists();
    }

    public static String extractFileNameFromFilePath(String filepath) {
        filepath = FileUtils.unifyFileSeparatorOfFilePath(filepath);
        return filepath.substring(filepath.lastIndexOf(File.separator));
    }

    public static String getFileNameByFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        return file.getName();
    }

    /**
     * the root of file is 'src/main/resources' or 'src/test/resources'
     *
     * @param fileClassPath For example: application.yml, i18n/test.properties
     * @return
     * @throws IOException
     */
    public static String getFilePathByFileClassPath(String fileClassPath) throws URISyntaxException {
        URL url = FileUtils.class.getClassLoader()
                .getResource(fileClassPath);
        return Paths.get(url.toURI()).toFile().getAbsolutePath();
    }

    public static String getFileNameByFileUrl(String fileUrl) {
        if (fileUrl == null) {
            return null;
        }
        String fileName = null;
        if (fileUrl.indexOf("/") > 0) {
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        }
        if (fileName.indexOf("?") > 0) {
            fileName = fileName.substring(0, fileName.indexOf("?"));
        }
        return fileName;
    }

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

    public static String removeIllegalCharactersFromFileName(String fileName) {
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

    public static boolean isPreviewFileOnBrowser(String fileName) {
        for (String suffix : BROWSER_PREVIEW_FILE_SUFFIXES) {
            if (fileName.toLowerCase().contains(suffix)) {
                return true;
            }
        }
        return false;
    }

    public static String unifyFileSeparatorOfFilePath(String filepath) {
        return filepath.replace("\\", File.separator).replace("/", File.separator);
    }
}
