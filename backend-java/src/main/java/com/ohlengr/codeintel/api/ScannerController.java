package com.ohlengr.codeintel.api;

import com.ohlengr.codeintel.model.ScanResult;
import com.ohlengr.codeintel.scanner.RepositoryScanner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScannerController {
    private final RepositoryScanner repositoryScanner;
    public ScannerController(RepositoryScanner repositoryScanner) {
        this.repositoryScanner = repositoryScanner;
    }

    @PostMapping("/scan")
    public ScanResult scanRepository(@RequestParam String path) {
        return repositoryScanner.scan(path);
    }
}
