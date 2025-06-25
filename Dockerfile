# ----------- 1. Build Stage -------------
FROM  maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and pre-fetch dependencies (speeds up rebuilds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application without tests
RUN mvn clean package -DskipTests


# ----------- 2. Run Stage ---------------
FROM eclipse-temurin:17-jre-jammy

# Set working directory inside container
WORKDIR /app

# Copy the generated JAR from the previous stage
COPY --from=build /app/target/*.jar MediPlan-0.0.1-SNAPSHOT.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "MediPlan-0.0.1-SNAPSHOT.jar"]
