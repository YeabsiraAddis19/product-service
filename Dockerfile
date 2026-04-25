# Use Eclipse Temurin JDK 17 as the base image
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set working directory
WORKDIR /build

# Copy Maven wrapper and configuration files
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Copy source code
COPY src ./src

# Build the application using Maven wrapper
RUN ./mvnw clean package -DskipTests -B

# Final stage
FROM eclipse-temurin:17-jre-alpine

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Change ownership to non-root user
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]