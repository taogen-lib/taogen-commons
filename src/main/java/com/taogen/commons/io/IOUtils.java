package com.taogen.commons.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Taogen
 */
public class IOUtils {
    public static final char[] NEWLINE_CHARACTERS = {'\r', '\n'};

    /**
     * The one-arguments constructors of FileReader always use the platform default encoding which is generally a bad idea.
     * Since Java 11 FileReader has also gained constructors that accept an encoding: new FileReader(file, charset) and new FileReader(fileName, charset).
     * In earlier versions of java, you need to use new InputStreamReader(new FileInputStream(pathToFile), <encoding>).
     *
     * @param file
     * @param charset
     * @return
     * @throws FileNotFoundException
     */
    public static BufferedReader getBufferedReaderWithCharset(File file,
                                                              Charset charset) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(
                new FileInputStream(file), charset));
    }

    public static BufferedReader getBufferedReaderWithCharset(InputStream inputStream,
                                                              Charset charset) {
        return new BufferedReader(new InputStreamReader(inputStream, charset));
    }

    /**
     * Note: the method caller is responsible for closing the inputStream
     *
     * @param inputStream
     * @return
     */
    public static String getTextFromInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = IOUtils.getBufferedReaderWithCharset(inputStream, StandardCharsets.UTF_8);
            int len;
            char[] buf = new char[1024];
            while ((len = reader.read(buf)) != -1) {
                stringBuilder.append(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String getTextFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = IOUtils.getBufferedReaderWithCharset(file, StandardCharsets.UTF_8)) {
            int len;
            char[] buf = new char[1024];
            while ((len = reader.read(buf)) != -1) {
                stringBuilder.append(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static byte[] convertInputStreamToBytes(InputStream inputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] convertFileToBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String removeNewLineCharacters(String source) {
        if (source == null) {
            return null;
        }
        return source.replace("\r\n", "")
                .replace("\r", "")
                .replace("\n", "");
    }

    public static boolean isNewlineChar(char c) {
        for (int i = 0; i < NEWLINE_CHARACTERS.length; i++) {
            if (c == NEWLINE_CHARACTERS[i]) {
                return true;
            }
        }
        return false;
    }

    private static int skipToEndOfLine(byte[] bytes, int i) {
        while (i < bytes.length) {
            if (IOUtils.isNewlineChar((char) bytes[i])) {
                if (i + 1 < bytes.length && !IOUtils.isNewlineChar((char) bytes[i + 1])) {
                    return i;
                }
            }
            i++;
        }
        return i;
    }
}
