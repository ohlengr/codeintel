package com.ohlengr.codeintel.api;

import com.ohlengr.codeintel.analysis.RepositoryAnalyzer;
import com.ohlengr.codeintel.model.RepositoryStructure;
import com.ohlengr.codeintel.model.ScanResult;
import com.ohlengr.codeintel.scanner.RepositoryScanner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RepositoryAnalysisController {
    private final RepositoryScanner repositoryScanner;
    private final RepositoryAnalyzer repositoryAnalyzer;

    public RepositoryAnalysisController(RepositoryScanner repositoryScanner, RepositoryAnalyzer repositoryAnalyzer){
        this.repositoryScanner = repositoryScanner;
        this.repositoryAnalyzer = repositoryAnalyzer;
    }

    @PostMapping("/analyze")
    public RepositoryStructure analyzeLocalRepository(@RequestParam("path") String repositoryPath){
        ScanResult scanResult = repositoryScanner.scan(repositoryPath);
        return repositoryAnalyzer.analyze(scanResult);
    }
}
