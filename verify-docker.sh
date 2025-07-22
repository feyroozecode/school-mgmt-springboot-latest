#!/bin/bash

echo "🔍 Docker Configuration Verification Script"
echo "==========================================="

# Check if Docker is available
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed or not in PATH"
    exit 1
fi

echo "✅ Docker is available"

# Check if Docker Compose is available
if command -v docker-compose &> /dev/null; then
    echo "✅ Docker Compose (standalone) is available"
    COMPOSE_CMD="docker-compose"
elif docker compose version &> /dev/null; then
    echo "✅ Docker Compose (plugin) is available"
    COMPOSE_CMD="docker compose"
else
    echo "❌ Docker Compose is not available"
    echo "Please install Docker Compose or use Docker Desktop"
    exit 1
fi

echo "📋 Using compose command: $COMPOSE_CMD"

# Verify required files exist
echo ""
echo "📁 Checking required files:"

files=("Dockerfile" "docker-compose.yml" "settings.xml" "secrets/db_password.txt" "secrets/jwt_secret.txt")
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file exists"
    else
        echo "❌ $file is missing"
    fi
done

# Check Docker images availability
echo ""
echo "🐳 Checking Docker images availability:"

images=("maven:3.9.5-openjdk-17-slim" "eclipse-temurin:17-jre-alpine" "postgres:15-alpine")
for image in "${images[@]}"; do
    echo "Checking $image..."
    if docker pull "$image" --quiet > /dev/null 2>&1; then
        echo "✅ $image is available"
    else
        echo "⚠️  $image might not be available or network issue"
    fi
done

echo ""
echo "🚀 To build and run the application:"
echo "1. Make sure Docker Desktop is running"
echo "2. Run: $COMPOSE_CMD up --build -d"
echo "3. Check logs: $COMPOSE_CMD logs -f backend"
echo "4. Access app: http://localhost:8080/actuator/health"

echo ""
echo "🔧 The main fix applied:"
echo "- Changed FROM openjdk:17-jre-alpine to FROM eclipse-temurin:17-jre-alpine"
echo "- This resolves the 'image not found' error you encountered"
