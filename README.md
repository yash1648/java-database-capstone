# Smart Clinic Management System

This is a Spring Boot web application for managing a clinic, integrating Thymeleaf for the front-end and using both PostgreSQL (via JPA) and MongoDB for data storage.

## Features
- **Thymeleaf Integration**: Fully integrated templates for Admin, Doctor, and Patient dashboards.
- **Dual Database Support**: PostgreSQL for relational data (Patients, Doctors, Appointments, Admins) and MongoDB for flexible data (Prescriptions).
- **REST APIs**: Comprehensive REST endpoints for all major entities.
- **Java 25 Ready**: Configured to compile and run using Java 25.

## Prerequisites
- **JDK 25**: Ensure you have JDK 25 installed and configured in your environment.
- **Maven**: Ensure Maven is installed.
- **Docker**: For running PostgreSQL and MongoDB containers.

## Setup Instructions

### 1. JDK 25 Installation
Download and install JDK 25 from a reliable source (e.g., [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://jdk.java.net/25/)).

Set your `JAVA_HOME` environment variable to point to the JDK 25 installation directory.

### 2. Database Setup
Use the provided `docker-compose.yml` to start the required databases:

```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port 5432 (Database: `cms`, User: `root`, Password: `root`)
- **MongoDB** on port 27017 (User: `root`, Password: `root`)

### 3. Build the Application
Navigate to the `app` directory and build the project using the Maven wrapper:

```bash
cd app
./mvnw clean install
```

### 4. Run the Application
Start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Verification Procedures

### 1. Compile Check
Ensure the project compiles without errors:
```bash
./mvnw compile
```

### 2. Run Tests
Execute the unit and integration tests:
```bash
./mvnw test
```

### 3. Verify Startup
Check the console output for a successful startup message:
`Started BackEndApplication in X seconds (process running for Y)`

## Project Structure
- `src/main/java`: Java source code (Controllers, Services, Models, Repositories).
- `src/main/resources/templates`: Thymeleaf templates.
- `src/main/resources/static`: Static assets (CSS, JS, Images).
- `src/test/java`: Unit and integration tests.

## Configuration
Configuration is managed via `app/src/main/resources/application.properties`.
Thymeleaf is configured for development mode with caching disabled.
