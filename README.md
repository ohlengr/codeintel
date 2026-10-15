# CodeIntel

CodeIntel is an early-stage project focused on exploring and understanding software repositories.

## Current Status

The project currently includes a Java Spring Boot backend with an initial local repository scanner.

Implemented functionality:

- Spring Boot application setup.
- Health-check API endpoint.
- Local repository directory validation.
- Recursive file discovery.
- Java source-file filtering.
- Structured scan results returned as JSON.

## Technology Stack

- Java 21
- Spring Boot
- Maven

## Getting Started

### Prerequisites

- JDK 21
- Maven

### Run the application

From the project root:

```bash
cd backend-java
mvn spring-boot:run
```

The application runs on port `8080` by default.

## API Endpoints

### Health Check

`GET /api/health`

Checks whether the backend is running.

### Scan Repository

`POST /api/scan?path=<repository-path>`

Scans a local directory and returns the discovered Java files as JSON.

Example:

```bash
curl -X POST "http://localhost:8080/api/scan?path=D:/projects/codeintel"
```

The scanner currently identifies regular files ending in `.java` and returns their paths, filenames, extensions, and the total number of discovered files.

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
```

## Development

CodeIntel is under active development. Its functionality and architecture may change as implementation progresses.

## License

License not yet specified.
