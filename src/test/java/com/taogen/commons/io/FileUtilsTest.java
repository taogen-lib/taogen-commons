package com.taogen.commons.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void getFileExtension() {
        assertNull(FileUtils.getFileExtension(null));
        assertNull(FileUtils.getFileExtension("test"));
        assertEquals("txt", FileUtils.getFileExtension("test.txt"));
        assertEquals("txt", FileUtils.getFileExtension("test.txt.txt"));
    }

    @Test
    void escapeFileName() {
        assertDoesNotThrow(() -> createFile(FileUtils.escapeFileName("test")));
        assertDoesNotThrow(() -> createFile(FileUtils.escapeFileName("test.txt")));
        List<String> specialFileNames = Arrays.asList(
                "test\\characters",
                "test/characters", "test:characters",
                "test*characters", "test?characters",
                "test\"characters", "test<characters",
                "test>characters", "test|characters",
                "test\rcharacters", "test\ncharacters",
                "test\tcharacters");
        for (String fileName : specialFileNames) {
            assertThrows(IllegalArgumentException.class, () -> createFile(fileName));
            assertDoesNotThrow(() -> createFile(FileUtils.escapeFileName(fileName)));
        }
    }

    void createFile(String fileName) throws IOException {
        Path path = Files.createTempFile(fileName, ".txt");
        System.out.println(path.toString());
        path.toFile().deleteOnExit();
    }
}
