package com.ohlengr.codeintel.scanner;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalRepositoryScanner implements RepositoryScanner {

    @Override
    public void scan(String repositoryPath) {
        Path path = Paths.get(repositoryPath);
        if(Files.exists(path) && Files.isDirectory(path)) {
            System.out.println("Valid repository found at: " + path);
        }else {
            throw new IllegalArgumentException("Invalid repository found at: " + path);
        }
    }
}
