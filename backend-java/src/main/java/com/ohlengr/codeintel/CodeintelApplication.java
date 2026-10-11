package com.ohlengr.codeintel;

import com.ohlengr.codeintel.scanner.LocalRepositoryScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CodeintelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeintelApplication.class, args);
	}

}
