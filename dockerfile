# Use an official OpenJDK runtime as a base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Add the Spring Boot artifact (your built JAR file) to the container
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]