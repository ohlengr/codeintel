package com.ohlengr.codeintel.scanner;

import com.ohlengr.codeintel.model.ScanResult;
import com.ohlengr.codeintel.model.SourceFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LocalRepositoryScanner implements RepositoryScanner {

    @Override
    public ScanResult scan(String repositoryPath) {
        Path path = Paths.get(repositoryPath);

        if(!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Invalid repository found at: " + path);
        }

        int count = 0;
        try (Stream<Path> pathStream = Files.walk(path)){
            List<SourceFile> sourceFile =  pathStream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".php"))
                    .map(p -> {
                        String fileName = p.getFileName().toString();
                        String extension = fileName.substring((fileName.lastIndexOf("."))+1);
                        return new SourceFile(p,fileName,extension);
                    }).toList();
            count = sourceFile.size();
            return new ScanResult(repositoryPath, count, sourceFile);
        }catch (IOException e) {
            throw new RuntimeException("Failed to read repository " + repositoryPath, e);
        }
    }
}
