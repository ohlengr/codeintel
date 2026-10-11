package com.ohlengr.codeintel.api;

import com.ohlengr.codeintel.scanner.RepositoryScanner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScannerController {
    private final RepositoryScanner localRepositoryScanner;
    public ScannerController(RepositoryScanner localRepositoryScanner) {
        this.localRepositoryScanner = localRepositoryScanner;
    }

    @PostMapping("/scan")
    public String scanRepository(@RequestParam String path) {
        localRepositoryScanner.scan(path);
        return "Scan initiated for: " + path;
    }
}
