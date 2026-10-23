# CodeIntel

CodeIntel is an early-stage open-source project focused on helping developers explore and understand software repositories.

The project is being developed incrementally, starting with local repository scanning and building toward deeper codebase understanding.

## Current Status

The project currently includes a Java Spring Boot backend with an initial local repository scanner.

Implemented functionality:

- Spring Boot application setup.
- Health-check API endpoint.
- Local repository directory validation.
- Recursive discovery of regular files.
- Case-insensitive PHP source-file filtering.
- Structured scan results returned as JSON.
- Automated tests for file discovery, invalid repository paths, and empty repositories.

## Technology Stack

- Java 21
- Spring Boot 4.1.1
- Maven

## Getting Started

### Prerequisites

- JDK 21
- Git (for cloning the repository)

The project includes a Maven Wrapper, so a separate Maven installation is not required.

### Run the Application

Open a terminal in the project root and run one of the following commands.

**Git Bash:**

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

### Health Check

`GET /api/health`

Checks whether the backend is running.

### Scan Repository

`POST /api/scan?path=<repository-path>`

Scans a local directory and returns discovered PHP files as JSON.

Example using a local PHP project:

```bash
curl -X POST "http://localhost:8080/api/scan?path=D:/path/to/your/php-project"
```

Replace the example path with an existing directory on the machine running the backend.

The scanner currently:

1. Validates that the supplied path exists and is a directory.
2. Recursively searches for regular files.
3. Includes files whose extension is `.php`, regardless of capitalization.
4. Returns the repository path, total number of discovered files, and file details.

Each discovered file currently includes its path, filename, and extension.

**Current limitation:** PHP is the only source-file extension supported by the scanner at this stage.

## Testing

The backend includes automated tests for `LocalRepositoryScanner`.

The tests use JUnit and temporary directories to verify that:

- PHP files are discovered in the repository root and nested directories.
- PHP extensions are matched without regard to capitalization.
- Non-PHP files are excluded.
- An invalid or nonexistent repository path causes an `IllegalArgumentException`.
- An empty repository produces an empty file list and a count of zero.

Run all backend tests from the `backend-java` directory:

```bash
./mvnw test
```

To run only the scanner tests:

```bash
./mvnw test -Dtest=LocalRepositoryScannerTest
```

## Project Structure

```text
codeintel/
└── backend-java/
    ├── pom.xml
    └── src/
        ├── main/
        │   └── java/
        │       └── com/ohlengr/codeintel/
        │           ├── api/
        │           ├── model/
        │           ├── scanner/
        │           └── CodeintelApplication.java
        └── test/
            └── java/
                └── com/ohlengr/codeintel/
                    └── scanner/
                        └── LocalRepositoryScannerTest.java
```

## Development

CodeIntel is under active development. Its architecture and functionality may change as the project evolves.

The current focus is improving repository-scanning reliability and establishing a foundation for future codebase analysis capabilities.

## License

A license has not yet been specified.