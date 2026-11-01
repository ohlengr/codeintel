package com.ohlengr.codeintel.scanner;

import com.ohlengr.codeintel.model.ScanResult;
import com.ohlengr.codeintel.model.SourceFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Service
public class LocalRepositoryScanner implements RepositoryScanner {

    @Override
    public ScanResult scan(String repositoryPath) {
        Path path = Paths.get(repositoryPath);

        if(!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Invalid repository found at: " + path);
        }

        List<Path> allPaths;
        try (Stream<Path> pathStream = Files.walk(path)){
            allPaths = pathStream.toList();
            List<String> directories =  allPaths.stream()
                    .filter(Files::isDirectory)
                    .filter(p -> !p.equals(path))
                    .map(p -> path.relativize(p).toString().replace("\\", "/"))
                    .toList();
            List<SourceFile> sourceFile = allPaths.stream()
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".php"))
                    .map(p -> {
                        String fileName = p.getFileName().toString();
                        int lastDotIndex = fileName.lastIndexOf(".");
                        String extension = lastDotIndex > 0 ? fileName.substring(lastDotIndex+1) : "";
                        String relativePath = path.relativize(p).toString().replace("\\", "/");
                        return new SourceFile(relativePath,fileName,extension);
                    }).toList();
            return new ScanResult(repositoryPath, sourceFile.size(), sourceFile, directories.size(), directories);
        }catch (IOException e) {
            throw new RuntimeException("Failed to read repository " + repositoryPath, e);
        }
    }
}
