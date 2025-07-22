#!/bin/bash

echo "🚀 Lancement de l'application School Management System..."

# Vérification Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker manquant. Installez Docker: https://docs.docker.com/get-docker/"
    exit 1
fi

# Nettoyage préalable
echo "🧹 Nettoyage des ressources Docker..."
docker-compose down --remove-orphans
docker system prune -f

# Vérification des fichiers requis
echo "🔍 Vérification des prérequis..."
if [ ! -f "Dockerfile" ]; then
    echo "❌ Dockerfile manquant!"
    exit 1
fi

if [ ! -f "docker-compose.yml" ]; then
    echo "❌ docker-compose.yml manquant!"
    exit 1
fi

if [ ! -d "secrets" ] || [ ! -f "secrets/db_password.txt" ] || [ ! -f "secrets/jwt_secret.txt" ]; then
    echo "❌ Fichiers secrets manquants! Créez le dossier 'secrets' avec db_password.txt et jwt_secret.txt"
    exit 1
fi

# Lancement avec Docker Compose
echo "🚀 Lancement des services..."
docker-compose up -d

# Augmentation du temps d'attente
echo "⏳ Attente du démarrage des services (60s)..."
sleep 60

# Vérification des conteneurs
echo "📊 État des conteneurs:"
docker-compose ps

# Vérification de santé avec retry
MAX_RETRIES=15
for ((i=1; i<=$MAX_RETRIES; i++)); do
    if curl -sSf http://localhost:8080/api/health > /dev/null; then
        echo -e "\n✅ Application prête: http://localhost:8080"
        echo "📚 Swagger UI: http://localhost:8080/swagger-ui.html"
        break
    else
        echo "⏳ Tentative $i/$MAX_RETRIES - Démarrage en cours..."
        sleep 10
    fi
done

# Gestion des erreurs
if ! curl -sSf http://localhost:8080/api/health > /dev/null; then
    echo -e "\n❌ Échec du démarrage. Logs du backend:"
    docker-compose logs --tail=100 backend
    echo "🔍 Débogage interactif:"
    echo "1. Inspecter le conteneur: docker exec -it $(docker-compose ps -q backend) bash"
    echo "2. Vérifier les fichiers: docker exec -it $(docker-compose ps -q backend) ls -l /app"
    exit 1
fi

echo -e "\n🎉 Application lancée avec succès!"
echo "Pour arrêter: docker-compose down"
echo "Pour les logs: docker-compose logs -f"