FROM eclipse-temurin:21

# Set the working directory
WORKDIR /

# Copy the project files to the working directory
COPY build/libs/dynamic-servers-templates-1.0-SNAPSHOT.jar /app/dynamic-servers-templates.jar
COPY src/main/resources/application.properties /app/application.properties

# Expose the ports
EXPOSE 8000:8000

# Set the entrypoint command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/dynamic-servers-templates.jar"]