# CodeIntel

CodeIntel is an early-stage open-source project focused on helping developers explore and understand software repositories.

The project is being developed incrementally, starting with local repository scanning and building toward deeper codebase understanding.

## Current Status

The project currently includes a Java Spring Boot backend with an initial local repository scanner.

**Implemented functionality:**

- Spring Boot application setup.
- Health-check API endpoint.
- Local repository directory validation.
- Recursive filesystem discovery.
- Case-insensitive PHP source-file filtering.
- Discovery of nested repository directories.
- Repository-relative file and directory paths.
- Structured scan results returned as JSON.
- Automated tests for file discovery, directory discovery, invalid repository paths, and empty repositories.

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

Scans a local directory and returns discovered PHP files and nested directories as JSON.

Example using a local PHP project:

**Git Bash / macOS / Linux:**

```bash
curl -X POST "http://localhost:8080/api/scan?path=/path/to/your/php-project"
```

**Windows:**

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
6. Returns file and directory information in the scan result.

Each discovered source file currently includes:

- `path`
- `fileName`
- `extension`

The scan result also includes:

- `repositoryPath`
- `totalFiles`
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

**Current limitation:** PHP is the only source-file extension supported by the scanner at this stage. Directory discovery is not restricted to PHP projects.

## Testing

The backend includes automated tests for `LocalRepositoryScanner`.

The tests use JUnit 5 and temporary directories to verify that:

- PHP files are discovered in the repository root and nested directories.
- PHP extensions are matched without regard to capitalization.
- Non-PHP files are excluded.
- Repository-relative file paths are returned correctly.
- Nested directories are discovered.
- The repository root is excluded from the directory count.
- An invalid or nonexistent repository path causes an `IllegalArgumentException`.
- An empty repository produces empty file and directory lists with counts of zero.

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
        │           ├── analysis/
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

The current development direction is to build on filesystem discovery and gradually introduce repository analysis and deeper codebase understanding.

The architectural principle is:

**Understand code first. Use AI second.**

## License

A license has not yet been specified.