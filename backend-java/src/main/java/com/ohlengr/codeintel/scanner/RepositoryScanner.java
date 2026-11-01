package com.ohlengr.codeintel.scanner;

import com.ohlengr.codeintel.model.ScanResult;

public interface RepositoryScanner {
    ScanResult scan(String repositoryPath);
}
