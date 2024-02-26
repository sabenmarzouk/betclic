# Step 1: Build the application using the Gradle image
FROM gradle:8.6-jdk21 as build

# Copy the project files
COPY --chown=gradle:gradle . /home/gradle/src

# Set the working directory
WORKDIR /home/gradle/src

# Build the JAR file using the Gradle wrapper
RUN ./gradlew build --no-daemon

# Step 2: Create the runtime image
FROM openjdk:21-jdk-slim

# Copy the built JAR from the build stage
COPY --from=build /home/gradle/src/betclic-infrastructure/build/libs/*.jar /app/

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/betclic-infrastructure.jar"]

