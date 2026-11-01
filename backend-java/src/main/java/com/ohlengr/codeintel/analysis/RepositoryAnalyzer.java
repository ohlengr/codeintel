package com.ohlengr.codeintel.analysis;

import com.ohlengr.codeintel.model.RepositoryStructure;
import com.ohlengr.codeintel.model.ScanResult;

public interface RepositoryAnalyzer {
    RepositoryStructure analyze(ScanResult scanResult);
}
