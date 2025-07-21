#!/bin/bash

# Script de lancement automatisé pour l'application School Management System
# Ce script construit et lance l'application avec Docker

set -e

echo "🚀 Lancement de l'application School Management System..."

# Vérifier si Docker est installé
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé. Veuillez installer Docker avant de continuer."
    exit 1
fi

# Vérifier si Docker Compose est installé
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose n'est pas installé. Veuillez installer Docker Compose avant de continuer."
    exit 1
fi

# Arrêter les conteneurs existants s'ils sont en cours d'exécution
echo "🛑 Arrêt des conteneurs existants..."
docker-compose down --remove-orphans

# Nettoyer les images et volumes orphelins (optionnel)
echo "🧹 Nettoyage des ressources Docker..."
docker system prune -f

# Construire et lancer les services
echo "🔨 Construction et lancement des services..."
docker-compose up --build -d

# Attendre que les services soient prêts
echo "⏳ Attente du démarrage des services..."
sleep 30

# Vérifier l'état des conteneurs
echo "📊 État des conteneurs:"
docker-compose ps

# Vérifier la santé de l'application
echo "🏥 Vérification de la santé de l'application..."
for i in {1..10}; do
    if curl -f http://localhost:8080/api/health > /dev/null 2>&1; then
        echo "✅ L'application est prête et accessible sur http://localhost:8080"
        echo "📚 Documentation API disponible sur http://localhost:8080/swagger-ui.html"
        break
    else
        echo "⏳ Tentative $i/10 - L'application n'est pas encore prête..."
        sleep 10
    fi
done

# Afficher les logs si l'application n'est pas prête
if ! curl -f http://localhost:8080/api/health > /dev/null 2>&1; then
    echo "❌ L'application ne répond pas. Voici les logs:"
    docker-compose logs backend
    exit 1
fi

echo ""
echo "🎉 Application lancée avec succès!"
echo "🌐 URL de l'application: http://localhost:8080"
echo "📖 Documentation API: http://localhost:8080/swagger-ui.html"
echo "🗄️  Base de données PostgreSQL: localhost:5432"
echo ""
echo "Pour arrêter l'application, utilisez: docker-compose down"
echo "Pour voir les logs, utilisez: docker-compose logs -f"

