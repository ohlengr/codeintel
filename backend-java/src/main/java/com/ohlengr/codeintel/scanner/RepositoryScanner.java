package com.ohlengr.codeintel.scanner;

import com.ohlengr.codeintel.model.ScanResult;

import java.util.List;

public interface RepositoryScanner {
    public ScanResult scan(String repositoryPath);
}
