package com.taogen.commons.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void appendDateTimeToFileName() {
        String fileName1 = "test.txt";
        String updateFileName1 = FileUtils.appendDateTimeToFileName(fileName1);
        System.out.println(updateFileName1);
        assertTrue(Pattern.compile("test_[0-9_-]+.txt").matcher(updateFileName1).matches());
        String fileName2 = "test";
        String updateFileName2 = FileUtils.appendDateTimeToFileName(fileName2);
        System.out.println(updateFileName2);
        assertTrue(Pattern.compile("test_[0-9_-]+").matcher(updateFileName2).matches());
    }

    @Test
    void getFilePathByFileClassPath() throws URISyntaxException, FileNotFoundException {
        assertThrows(FileNotFoundException.class, () -> {
            FileUtils.getFilePathByFileClassPath("test.txt");
        });
        String filePath = FileUtils.getFilePathByFileClassPath("static/test.txt");
        System.out.println(filePath);
        assertTrue(filePath.endsWith(new StringBuilder()
                .append("static")
                .append(File.separator)
                .append("test.txt")
                .toString()));
    }

    @Test
    void doesFileExist() throws FileNotFoundException {
        String tempDir = DirectoryUtils.getTempDir();
        assertTrue(FileUtils.doesFileExist(tempDir));
        String testFile = new StringBuilder()
                .append(tempDir)
                .append(File.separator)
                .append("test")
                .append(System.currentTimeMillis())
                .append(".txt")
                .toString();
        assertFalse(FileUtils.doesFileExist(testFile));
    }

    @Test
    void getFileExtension() {
        assertNull(FileUtils.getFileExtension(null));
        assertNull(FileUtils.getFileExtension("test"));
        assertEquals("txt", FileUtils.getFileExtension("test.txt"));
        assertEquals("txt", FileUtils.getFileExtension("test.txt.txt"));
    }

    @Test
    void removeIllegalCharactersFromFileName() {
        Map<String, String> specialFileNames = new HashMap<>();
        specialFileNames.put("test\\characters", "test-characters");
        specialFileNames.put("test/characters", "test-characters");
        specialFileNames.put("test:characters", "test-characters");
        specialFileNames.put("test*characters", "test-characters");
        specialFileNames.put("test?characters", "test-characters");
        specialFileNames.put("test\"characters", "test-characters");
        specialFileNames.put("test<characters", "test-characters");
        specialFileNames.put("test>characters", "test-characters");
        specialFileNames.put("test|characters", "test-characters");
        specialFileNames.put("test\rcharacters", "test-characters");
        specialFileNames.put("test\ncharacters", "test-characters");
        specialFileNames.put("test\tcharacters", "test-characters");
        for (Map.Entry<String, String> entry : specialFileNames.entrySet()) {
            assertEquals(entry.getValue(), FileUtils.removeIllegalCharactersFromFileName(entry.getKey()));
        }
    }

    void createFile(String fileName) throws IOException {
        Path path = Files.createTempFile(fileName, ".txt");
        System.out.println(path.toString());
        path.toFile().deleteOnExit();
    }
}
