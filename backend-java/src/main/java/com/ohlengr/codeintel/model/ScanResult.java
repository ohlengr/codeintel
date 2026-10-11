package com.ohlengr.codeintel.model;

import java.util.List;

public record ScanResult(String repositoryPath, long totalFiles, List<SourceFile> files) {
}
