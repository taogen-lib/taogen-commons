package com.taogen.commons.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Taogen
 */
public class IOUtils {
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
}
