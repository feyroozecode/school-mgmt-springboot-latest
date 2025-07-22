# ================================
# Multi-stage Production Dockerfile
# ================================

# Stage 1: Build stage
FROM maven:3.9.5-openjdk-17-slim AS builder

# Set build arguments
ARG MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
ENV MAVEN_OPTS=${MAVEN_OPTS}

# Create app user for security
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Copy Maven settings
COPY settings.xml /usr/share/maven/ref/settings-docker.xml

WORKDIR /app

# Copy build files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies (cached layer)
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml verify clean --fail-never \
    && mvn -B -s /usr/share/maven/ref/settings-docker.xml dependency:go-offline

# Copy source code
COPY src ./src

# Build application with optimizations
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml clean package -DskipTests \
    -Dmaven.javadoc.skip=true \
    -Dmaven.source.skip=true \
    -Dmaven.test.skip=true \
    && cp target/*.jar /app/application.jar \
    && test -f /app/application.jar || (echo "ERROR: JAR file not found!" && exit 1)

# Stage 2: Runtime stage
FROM eclipse-temurin:17.0.9_9-jre-jammy AS runtime

# Install security updates and required packages
RUN apt-get update && apt-get upgrade -y && \
    apt-get install -y --no-install-recommends \
        curl \
        tzdata \
    && rm -rf /var/lib/apt/lists/*

# Set timezone
ENV TZ=Africa/Niamey
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Create non-root user for security
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Create app directory and set permissions
WORKDIR /app
RUN chown -R appuser:appgroup /app

# Copy JAR from builder stage
COPY --from=builder --chown=appuser:appgroup /app/application.jar /app/

# Verify JAR exists and set permissions
RUN test -f /app/application.jar || (echo "FATAL: JAR file missing!" && exit 1) \
    && chmod 500 /app/application.jar

# Switch to non-root user
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Expose port
EXPOSE 8080

# JVM optimizations for production
ENV JAVA_OPTS="-server -Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker"

# Start application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/application.jar"]