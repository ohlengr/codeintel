package com.ohlengr.codeintel.model;

import java.nio.file.Path;

public record SourceFile(Path path, String fileName, String extension) {
}
