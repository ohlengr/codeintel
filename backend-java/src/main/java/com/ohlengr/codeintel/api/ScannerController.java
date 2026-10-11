package com.ohlengr.codeintel.api;

import com.ohlengr.codeintel.scanner.LocalRepositoryScanner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScannerController {
    private final LocalRepositoryScanner localRepositoryScanner;
    public ScannerController(LocalRepositoryScanner localRepositoryScanner) {
        this.localRepositoryScanner = localRepositoryScanner;
    }

    @PostMapping("/scan")
    public String scanRepository(@RequestParam String path) {
        localRepositoryScanner.scan(path);
        return "Scan initiated for: " + path;
    }
}
