package com.ohlengr.codeintel.scanner;

import com.ohlengr.codeintel.model.ScanResult;

import com.ohlengr.codeintel.model.SourceFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocalRepositoryScannerTest {
    @TempDir
    public Path tempDir;

    @Test
    public void testLocalRepositoryScanner() throws IOException {
        Files.createFile(tempDir.resolve("index.php"));
        Files.createFile(tempDir.resolve("readme.txt"));
        Path ajaxTempDirPath = Files.createDirectory(tempDir.resolve("ajax"));
        Files.createFile(ajaxTempDirPath.resolve("request.php"));

//        try(var paths = Files.walk(tempDir)){
//            paths.forEach(System.out::println);
//        }

        LocalRepositoryScanner localRepositoryScanner = new LocalRepositoryScanner();
        ScanResult scanResult = localRepositoryScanner.scan(tempDir.toString());

        assertEquals(2, scanResult.totalFiles());

        List<String> fileName = scanResult.files().stream().map(SourceFile::fileName).sorted().toList();

        assertEquals(
                List.of("index.php", "request.php"),
                fileName
        );
    }

    @Test
    void testScanWithInvalidPath() {
        LocalRepositoryScanner scanner = new LocalRepositoryScanner();

        String invalidPath = tempDir.resolve("non-existent-directory").toString();

        assertThrows(
                IllegalArgumentException.class,
                () -> scanner.scan(invalidPath)
        );
    }

    @Test
    void testScanEmptyRepository() {
        LocalRepositoryScanner scanner = new LocalRepositoryScanner();
        ScanResult scanResult = scanner.scan(tempDir.toString());

        assertEquals(0, scanResult.totalFiles());
        assertTrue(scanResult.files().isEmpty());
    }
}
