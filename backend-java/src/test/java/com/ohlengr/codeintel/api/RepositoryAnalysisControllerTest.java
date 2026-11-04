package com.ohlengr.codeintel.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryAnalysisControllerTest {

    @TempDir
    private Path tempDir;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void analyze_shouldReturn200_whenRepositoryPathIsValid() throws Exception{
        Files.createFile(tempDir.resolve("index.php"));
        Path ajaxDir = Files.createDirectories(tempDir.resolve("ajax"));
        Files.createDirectories(ajaxDir.resolve("Model"));
        Files.createDirectories(tempDir.resolve("app"));

        mockMvc.perform(
                post("/api/analyze")
                        .param("path", tempDir.toString())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topLevelDirectories", containsInAnyOrder("ajax", "app")));
    }

    @Test
    public void analyze_shouldReturn400_whenRepositoryPathIsInvalid() throws Exception{
        mockMvc.perform(
                post("/api/analyze")
                        .param("path","/invalid/repository/path")
        ).andExpect(status().isBadRequest()).andExpect(content().string(
                "Invalid repository found at: /invalid/repository/path"
        ));
    }

}
