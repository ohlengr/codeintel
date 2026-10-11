package com.ohlengr.codeintel.api;

import com.ohlengr.codeintel.model.ScanResult;
import com.ohlengr.codeintel.scanner.LocalRepositoryScanner;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScannerController {
    private final LocalRepositoryScanner localRepositoryScanner;
    public ScannerController(LocalRepositoryScanner localRepositoryScanner) {
        this.localRepositoryScanner = localRepositoryScanner;
    }

    @PostMapping("/scan")
    public ScanResult scanRepository(@RequestParam String path) {
        return localRepositoryScanner.scan(path);
    }
}
