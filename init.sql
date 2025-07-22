-- Script d'initialisation pour la base de données school_mgmt

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS school_mgmt;

-- Utiliser la base de données
\c school_mgmt;

-- Créer un utilisateur admin par défaut (sera géré par l'application Spring Boot)
-- Les tables seront créées automatiquement par Hibernate

-- Insérer des données de test (optionnel)
-- Ces données seront insérées après la création des tables par l'application

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);
-- Ajoutez d'autres scripts d'initialisation ici