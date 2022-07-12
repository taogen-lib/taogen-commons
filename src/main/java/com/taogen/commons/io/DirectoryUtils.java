package com.taogen.commons.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Taogen
 */
public class DirectoryUtils {
    /**
     * Pack directory
     *
     * @param sourceDirPath
     * @param zipFilePath
     * @throws IOException
     */
    public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path zipPath = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Path sourcePath = Paths.get(sourceDirPath);
            Files.walk(sourcePath)
                    .filter(filepath -> !Files.isDirectory(filepath))
                    .forEach(filepath -> {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(filepath).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(filepath, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    public static String getFileDirFromFilePath(String filepath) {
        filepath = FileUtils.unifyFileSeparatorOfFilePath(filepath);
        return filepath.substring(0, filepath.lastIndexOf(File.separator));
    }

    /**
     * ensure directory of filepath exists
     * @param filepath
     */
    public static void createDirOfFilePathIfNotExist(String filepath) {
        File fileDir = new File(getFileDirFromFilePath(filepath));
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }
}
