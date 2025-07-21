-- Script d'initialisation pour la base de données school_mgmt

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS school_mgmt;

-- Utiliser la base de données
\c school_mgmt;

-- Créer un utilisateur admin par défaut (sera géré par l'application Spring Boot)
-- Les tables seront créées automatiquement par Hibernate

-- Insérer des données de test (optionnel)
-- Ces données seront insérées après la création des tables par l'application

