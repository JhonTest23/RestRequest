# 🏃 Stage 1: Runtime only (no Gradle build inside Docker)
FROM eclipse-temurin:21.0.8_9-jre-ubi9-minimal AS runtime

# Set working directory
WORKDIR /app

# Copy your prebuilt JAR into the container
COPY build/libs/RestApiRequest.jar app.jar

# Expose port (optional, but useful for clarity)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]