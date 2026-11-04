package com.ohlengr.codeintel.analysis;

import com.ohlengr.codeintel.model.RepositoryStructure;
import com.ohlengr.codeintel.model.ScanResult;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LocalRepositoryAnalyzer implements RepositoryAnalyzer{
    @Override
    public RepositoryStructure analyze(ScanResult scanResult) {
        return new RepositoryStructure(Objects.requireNonNull(scanResult, "ScanResult cannot be null").directories().stream().filter(d -> !d.contains("/")).toList());
    }
}
