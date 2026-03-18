# ---------------------------------------------------
# Stage 1: Build the Spring Boot application
# ---------------------------------------------------
# Use a Maven image with JDK 17 to compile the project
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Maven configuration file
# This allows Maven to download dependencies
COPY pom.xml .

# Copy application source code
COPY src ./src

# Build the Spring Boot application
# -DskipTests speeds up the build by skipping test execution
RUN mvn clean package -DskipTests


# ---------------------------------------------------
# Stage 2: Run the application
# ---------------------------------------------------
# Use a lightweight JRE image to run the compiled application
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory for the runtime container
WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]