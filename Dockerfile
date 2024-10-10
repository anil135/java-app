


# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the local target directory to the container
COPY target/postgres-connection-1.0-SNAPSHOT.jar app.jar

# The entry point of the container, using environment variables for DB connection
ENTRYPOINT ["java", "-jar", "app.jar"]
