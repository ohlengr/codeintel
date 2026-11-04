package com.ohlengr.codeintel.analysis;

import com.ohlengr.codeintel.model.RepositoryStructure;
import com.ohlengr.codeintel.model.ScanResult;
import com.ohlengr.codeintel.model.SourceFile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocalRepositoryAnalyzerTest{

    @Test
    public void analyze_shouldReturnOnlyTopLevelDirectories(){
        List<String> directories = new ArrayList<String>();
        directories.add("app");
        directories.add("app/Models");
        directories.add("app/Http");
        directories.add("routes");
        directories.add("routes/api");
        directories.add("config");
        ScanResult scanResult = new ScanResult("/user/home/php",0, List.of(), 6, directories);
        LocalRepositoryAnalyzer localRepositoryAnalyzer = new LocalRepositoryAnalyzer();
        RepositoryStructure repositoryStructure = localRepositoryAnalyzer.analyze(scanResult);

        assertEquals(List.of("app", "routes", "config"), repositoryStructure.topLevelDirectories());
    }

    @Test
    public void analyze_shouldReturnEmptyStructure_whenNoDirectoriesExist(){
        ScanResult scanResult = new ScanResult("/user/home/empty-project", 0, List.of(), 0, List.of());
        LocalRepositoryAnalyzer localRepositoryAnalyzer = new LocalRepositoryAnalyzer();
        RepositoryStructure repositoryStructure = localRepositoryAnalyzer.analyze(scanResult);

        assertTrue(repositoryStructure.topLevelDirectories().isEmpty());
    }

    @Test
    public void analyze_shouldThrowException_whenScanResultIsNull(){
        LocalRepositoryAnalyzer localRepositoryAnalyzer = new LocalRepositoryAnalyzer();
        assertThrows(NullPointerException.class, () -> {
            localRepositoryAnalyzer.analyze(null);
        });
    }

}
