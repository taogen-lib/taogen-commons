package com.taogen.commons.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
     * @param filePath
     * @param charset
     * @return
     * @throws FileNotFoundException
     */
    public static BufferedReader getBufferedReaderWithCharset(String filePath,
                                                              Charset charset) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), charset));
    }

    public static String getTextFromFile(String textFilePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = IOUtils.getBufferedReaderWithCharset(textFilePath, StandardCharsets.UTF_8)) {
            int len;
            char[] buf = new char[1024];
            while ((len = reader.read(buf)) != -1) {
                stringBuilder.append(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
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
