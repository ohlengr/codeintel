package com.ohlengr.codeintel.model;

import java.util.List;

public record RepositoryStructure(
        List<String> topLevelDirectories
) {
    public RepositoryStructure{
        topLevelDirectories = List.copyOf(topLevelDirectories);
    }
}
