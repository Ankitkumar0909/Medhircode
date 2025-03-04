# Use an optimized OpenJDK 23 slim base image
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the specific JAR file into the container
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (default for Spring Boot)
EXPOSE 4000

# Command to run the application
CMD ["java", "-jar", "app.jar"]
