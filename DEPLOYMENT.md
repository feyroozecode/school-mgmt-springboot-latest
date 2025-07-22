# 🚀 Production Deployment Guide
## École Management System - Niger

### Prerequisites

1. **Docker & Docker Compose** installed
2. **Minimum System Requirements:**
   - RAM: 2GB minimum, 4GB recommended
   - CPU: 2 cores minimum
   - Storage: 10GB available space
   - OS: Linux (Ubuntu 20.04+), macOS, or Windows with WSL2

### Quick Start

1. **Clone and prepare the project:**
   ```bash
   git clone <repository-url>
   cd school-mgmt-springboot-latest
   chmod +x launch.sh
   ```

2. **Configure secrets:**
   ```bash
   # Secrets are already created with secure defaults
   # For production, update these files:
   echo "your_secure_db_password" > secrets/db_password.txt
   echo "your_jwt_secret_key_here" > secrets/jwt_secret.txt
   ```

3. **Launch the application:**
   ```bash
   ./launch.sh
   ```

### Manual Deployment Steps

1. **Build and start services:**
   ```bash
   docker-compose up --build -d
   ```

2. **Check service status:**
   ```bash
   docker-compose ps
   docker-compose logs -f backend
   ```

3. **Access the application:**
   - API: http://localhost:8080
   - Health Check: http://localhost:8080/actuator/health
   - API Documentation: http://localhost:8080/swagger-ui.html

### Production Security Checklist

- ✅ **Secrets Management**: Using Docker secrets instead of environment variables
- ✅ **Non-root User**: Application runs as non-privileged user
- ✅ **Resource Limits**: Memory and CPU limits configured
- ✅ **Health Checks**: Comprehensive health monitoring
- ✅ **Security Options**: no-new-privileges, capability dropping
- ✅ **Network Isolation**: Custom bridge network
- ✅ **Read-only Filesystem**: Database container uses read-only root filesystem

### Monitoring & Maintenance

1. **View logs:**
   ```bash
   docker-compose logs -f backend
   docker-compose logs -f postgres
   ```

2. **Database backup:**
   ```bash
   docker exec school-mgmt-postgres pg_dump -U school_user school_mgmt > backup.sql
   ```

3. **Update application:**
   ```bash
   docker-compose down
   git pull
   docker-compose up --build -d
   ```

4. **Scale services (if needed):**
   ```bash
   docker-compose up --scale backend=2 -d
   ```

### Troubleshooting

**Application won't start:**
- Check logs: `docker-compose logs backend`
- Verify secrets files exist and have correct permissions
- Ensure PostgreSQL is healthy: `docker-compose ps`

**Database connection issues:**
- Verify PostgreSQL health: `docker exec school-mgmt-postgres pg_isready -U school_user`
- Check network connectivity: `docker network ls`

**Performance issues:**
- Monitor resource usage: `docker stats`
- Adjust memory limits in docker-compose.yml
- Check database connection pool settings

### Environment Variables

Key environment variables (configured in docker-compose.yml):
- `SPRING_PROFILES_ACTIVE=docker`
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update`
- `LOGGING_LEVEL_ROOT=INFO`
- `TZ=Africa/Niamey`

### File Structure

```
school-mgmt-springboot-latest/
├── Dockerfile                 # Multi-stage production build
├── docker-compose.yml         # Production orchestration
├── launch.sh                 # Automated deployment script
├── settings.xml              # Maven build configuration
├── secrets/                  # Secure credentials
│   ├── db_password.txt
│   └── jwt_secret.txt
├── src/main/resources/
│   └── application-docker.properties
└── init.sql                  # Database initialization
```

### Support

For issues or questions:
1. Check application logs
2. Verify all prerequisites are met
3. Ensure all configuration files are present
4. Review this deployment guide

---
**🎉 Your École Management System is now production-ready!**
