# Use a lightweight OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the fat JAR from the target directory to the container
COPY target/bookstore-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on (default: 8080)
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
