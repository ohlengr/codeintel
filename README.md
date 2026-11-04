# CodeIntel

CodeIntel is an early-stage open-source project focused on helping developers explore and understand software repositories.

The project is being developed incrementally, starting with local repository scanning and building toward deeper codebase understanding.

**Core principle: Understand code first. Use AI second.**

## Current Status

The project currently includes a Java Spring Boot backend with local repository scanning and initial repository structure analysis.

**Implemented functionality:**

- Spring Boot application setup.
- Health-check API endpoint.
- Local repository directory validation.
- Recursive filesystem discovery using a single traversal.
- Case-insensitive PHP source-file filtering.
- Discovery of nested repository directories.
- Repository-relative file and directory paths.
- Structured scan results returned as JSON.
- Repository analysis using scanner results.
- Identification of top-level repository directories.
- Separate API endpoints for scanning and repository analysis.
- Centralized exception handling using Spring `@RestControllerAdvice`.
- HTTP 400 responses for invalid repository paths.
- Automated unit and integration tests.

## Technology Stack

- Java 21
- Spring Boot 4.1.1
- Maven
- JUnit 5
- Spring Boot Test
- MockMvc

## Getting Started

### Prerequisites

- JDK 21
- Git (for cloning the repository)

The project includes a Maven Wrapper, so a separate Maven installation is not required.

### Run the Application

Open a terminal in the project root and execute one of the following commands.

**Git Bash / macOS / Linux:**

```bash
cd backend-java
./mvnw spring-boot:run
```

**Windows PowerShell:**

```powershell
cd backend-java
.\mvnw.cmd spring-boot:run
```

The application runs on port `8080` by default.

## API Endpoints

### 1. Health Check

`GET /api/health`

Checks whether the backend application is running.

### 2. Scan Repository

`POST /api/scan?path=<repository-path>`

Scans a local repository directory and returns discovered PHP source files and directories.

**Example — macOS / Linux:**

```bash
curl -X POST "http://localhost:8080/api/scan?path=/path/to/your/php-project"
```

**Example — Windows:**

```bash
curl -X POST "http://localhost:8080/api/scan?path=D:/path/to/your/php-project"
```

Replace the example path with an existing directory on the machine running the backend.

The scanner currently:

1. Validates that the supplied path exists and is a directory.
2. Recursively discovers filesystem paths in a single traversal.
3. Identifies regular files with the `.php` extension, regardless of capitalization.
4. Identifies nested directories, excluding the repository root.
5. Converts discovered paths into repository-relative paths.
6. Returns file and directory information in a structured scan result.

Each discovered source file includes:

- `path`
- `fileName`
- `extension`

The scan result also includes:

- `repositoryPath`
- `totalFiles`
- `files`
- `totalDirectories`
- `directories`

**Example JSON response:**

```json
{
  "repositoryPath": "/path/to/your/php-project",
  "totalFiles": 3,
  "files": [
    {
      "path": "login.php",
      "fileName": "login.php",
      "extension": "php"
    },
    {
      "path": "index.php",
      "fileName": "index.php",
      "extension": "php"
    },
    {
      "path": "ajax/request.php",
      "fileName": "request.php",
      "extension": "php"
    }
  ],
  "totalDirectories": 1,
  "directories": [
    "ajax"
  ]
}
```

### 3. Analyze Repository Structure

`POST /api/analyze?path=<repository-path>`

Analyzes the repository structure using the results produced by the repository scanner.

The analysis currently identifies top-level directories from the discovered directory paths.

**Example — macOS / Linux:**

```bash
curl -X POST "http://localhost:8080/api/analyze?path=/path/to/your/php-project"
```

**Example — Windows:**

```bash
curl -X POST "http://localhost:8080/api/analyze?path=D:/path/to/your/php-project"
```

**Example JSON response:**

```json
{
  "topLevelDirectories": [
    "app",
    "ajax"
  ]
}
```

The analyzer currently:

1. Receives the `ScanResult` produced by the scanner.
2. Examines the discovered repository directory paths.
3. Identifies top-level directories.
4. Excludes nested directories from the top-level result.
5. Returns the analyzed structure as a `RepositoryStructure` object.

The analyzer does not perform another filesystem traversal. It works with the information already collected by the scanner.

### Error Handling

Invalid repository paths return an HTTP `400 Bad Request` response.

**Example response body:**

```text
Invalid repository found at: /invalid/repository/path
```

Centralized exception handling is implemented using Spring's `@RestControllerAdvice` and `@ExceptionHandler`.

## Current Limitations

- PHP is currently the only supported source-file extension.
- Directory discovery is not restricted to PHP projects.
- Repository analysis currently identifies only top-level directories.
- Deeper code understanding, including controllers, routes, models, services, and framework detection, has not yet been implemented.
- The application currently analyzes local repository paths accessible to the machine running the backend.

## Testing

The backend includes automated unit and integration tests using JUnit 5 and Spring Boot Test.

**Current test coverage:**

**LocalRepositoryScannerTest**
- Discovers PHP files in repository root and nested directories.
- Matches PHP extensions without regard to capitalization.
- Excludes non-PHP files.
- Returns repository-relative file paths.
- Discovers nested directories.
- Excludes the repository root from directory results.
- Handles invalid or nonexistent repository paths.
- Handles empty repositories.

**LocalRepositoryAnalyzerTest**
- Identifies only top-level directories.
- Returns an empty structure when no directories exist.
- Rejects a null scan result.

**RepositoryAnalysisControllerTest**
- Returns HTTP 200 for a valid repository path.
- Returns HTTP 400 for an invalid repository path.
- Verifies the error response body.
- Uses JUnit temporary directories instead of relying on a fixed local repository path.

**Run all backend tests:**

From the `backend-java` directory:

```bash
./mvnw test
```

**Run scanner tests only:**

```bash
./mvnw test -Dtest=LocalRepositoryScannerTest
```

**Run analyzer tests only:**

```bash
./mvnw test -Dtest=LocalRepositoryAnalyzerTest
```

**Run repository analysis controller tests only:**

```bash
./mvnw test -Dtest=RepositoryAnalysisControllerTest
```

**Latest test result:**

- Total tests: 9
- Failures: 0
- Errors: 0
- Skipped: 0
- Build status: SUCCESS

## Project Structure

```text
codeintel/
├── backend-java/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   └── java/
│       │       └── com/ohlengr/codeintel/
│       │           ├── analysis/
│       │           │   ├── LocalRepositoryAnalyzer.java
│       │           │   └── RepositoryAnalyzer.java
│       │           ├── api/
│       │           │   ├── GlobalExceptionHandler.java
│       │           │   ├── RepositoryAnalysisController.java
│       │           │   └── ScannerController.java
│       │           ├── model/
│       │           │   ├── RepositoryStructure.java
│       │           │   ├── ScanResult.java
│       │           │   └── SourceFile.java
│       │           ├── scanner/
│       │           │   ├── LocalRepositoryScanner.java
│       │           │   └── RepositoryScanner.java
│       │           └── CodeintelApplication.java
│       └── test/
│           └── java/
│               └── com/ohlengr/codeintel/
│                   ├── analysis/
│                   │   └── LocalRepositoryAnalyzerTest.java
│                   ├── api/
│                   │   └── RepositoryAnalysisControllerTest.java
│                   ├── scanner/
│                   │   └── LocalRepositoryScannerTest.java
│                   └── CodeintelApplicationTests.java
├── docs/
└── README.md
```

## Development Direction

CodeIntel is under active development. Its architecture and functionality will evolve as repository understanding becomes deeper.

The planned development direction includes:

- Deeper repository structure analysis.
- Identification of controllers, routes, models, and services.
- Framework and architectural pattern detection.
- Dependency and code relationship analysis.
- Structured engineering documentation.
- AI-assisted codebase insights built on top of analyzed repository information.

These are future development goals, not currently implemented features.

## Architectural Principle

**Understand code first. Use AI second.**

CodeIntel aims to build a reliable understanding of a software repository before using AI to explain, document, or reason about its code.

## License

A license has not yet been specified.