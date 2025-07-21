# Exigences Spécifiques Franco-Arabes

Ce document détaille les modifications et ajouts nécessaires pour supporter les écoles franco-arabes dans l'application de gestion scolaire.

## 1. Gestion des Noms Bilingues (Français/Arabe)

Pour les élèves inscrits dans les écoles franco-arabes, il est essentiel de pouvoir enregistrer et afficher leurs noms en français et en arabe.

### Entité `Student`
Les champs suivants ont été ajoutés ou modifiés dans l'entité `Student`:
- `firstNameArabic`: Prénom de l'élève en arabe.
- `lastNameArabic`: Nom de famille de l'élève en arabe.
- `classNameArabic`: Nom de la classe en arabe.
- `preferredLanguage`: Langue préférée de l'élève (fr ou ar).
- `primaryScript`: Script principal utilisé pour l'élève (LATIN ou ARABIC).

### Entité `Grade`
- `subjectArabic`: Nom de la matière en arabe.

## 2. Bulletins Scolaires Bilingues

Les bulletins scolaires doivent être générés en version bilingue (français et arabe) pour les élèves des écoles franco-arabes. Cela implique:
- Affichage des noms de l'élève en français et en arabe.
- Affichage des noms des matières en français et en arabe.
- Affichage des notes et appréciations.
- Adaptation de la mise en page pour supporter l'écriture de droite à gauche (RTL) pour l'arabe.
- Utilisation de polices supportant les caractères arabes.

## 3. Correction des Erreurs Métier et Bonnes Pratiques

Le projet sera revu pour s'assurer que les logiques métier sont claires, compréhensibles et suivent les meilleures pratiques de développement d'applications scolaires modernes. Cela inclut:
- Validation robuste des données d'entrée.
- Gestion des exceptions claire et informative.
- Séparation des préoccupations (services, contrôleurs, repositories).
- Utilisation de DTOs pour les transferts de données.
- Optimisation des requêtes de base de données.

## 4. Containerisation Docker

Pour faciliter le déploiement et la gestion de l'application, un `Dockerfile` sera ajouté pour le backend Spring Boot. Un fichier `docker-compose.yml` sera également mis à jour pour inclure le service backend et la base de données PostgreSQL. Un script `launch.sh` sera fourni pour automatiser le processus de build et de démarrage de l'application via Docker.

## 5. Tests et Documentation

- **Tests Unitaires**: Ajout de tests unitaires pour les nouvelles fonctionnalités et les logiques métier critiques.
- **Documentation API**: Mise à jour de la documentation Swagger/OpenAPI pour refléter les nouvelles API et les champs bilingues.
- **Guide d'Utilisation**: Création d'un guide d'utilisation pour les fonctionnalités spécifiques aux écoles franco-arabes.

