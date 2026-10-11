package com.ohlengr.codeintel.scanner;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class LocalRepositoryScanner implements RepositoryScanner {

    @Override
    public void scan(String repositoryPath) {
        Path path = Paths.get(repositoryPath);

        if(!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Invalid repository found at: " + path);
        }

        try (Stream<Path> pathStream = Files.walk(path)){
            pathStream
                    .filter(Files::isRegularFile)
                    .filter(p -> !p.toString().contains(".gitignore"))
                    .filter(p -> !p.toString().contains(".git"))
                    .filter(p -> !p.toString().contains(".idea"))
                    .filter(p -> !p.toString().contains("target"))
                    .filter(p -> !p.toString().contains("node_modules"))
                    .forEach(p -> System.out.println("Found file: " + p));
        }catch (IOException e) {
            throw new RuntimeException("Failed to read repository " + e);
        }
    }
}
