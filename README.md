# Système de Gestion Scolaire - Niger

## Vue d'ensemble

Le Système de Gestion Scolaire Niger est une application web moderne développée avec Spring Boot et PostgreSQL, spécialement conçue pour répondre aux besoins du système éducatif nigérien. Cette solution complète permet la gestion des étudiants, des paiements, des notes académiques et des notifications dans le contexte spécifique du Niger.

## Caractéristiques Principales

### 🎓 Gestion des Étudiants
- Inscription et profils complets des étudiants
- Support du système éducatif nigérien (CI à Master)
- Informations familiales et tuteurs
- Gestion des langues parlées (Français, Haoussa, Zarma)
- Suivi du statut académique

### 💰 Gestion des Paiements
- Support du Franc CFA (XOF)
- Méthodes de paiement locales (Mobile Money, Orange Money, etc.)
- Génération automatique de reçus PDF
- Suivi des paiements échelonnés
- Rapports financiers détaillés

### 📊 Système Académique
- Gestion des notes et évaluations
- Bulletins scolaires automatisés
- Calcul des moyennes par matière et trimestre
- Suivi des performances académiques

### 📱 Notifications Multi-Canal
- SMS prioritaire (adapté au contexte nigérien)
- Notifications email
- Support multilingue (Français, Haoussa, Zarma)
- Rappels de paiement automatiques

## Architecture Technique

### Technologies Utilisées
- **Backend**: Spring Boot 3.2.1
- **Base de données**: PostgreSQL 14+
- **Sécurité**: Spring Security + JWT
- **Documentation**: OpenAPI/Swagger
- **Tests**: JUnit 5 + Testcontainers
- **Build**: Maven 3.6+

### Structure du Projet
```
school-mgmt-springboot/
├── src/
│   ├── main/
│   │   ├── java/ne/ecole/schoolmgmt/
│   │   │   ├── config/          # Configurations
│   │   │   ├── controller/      # Contrôleurs REST
│   │   │   ├── dto/            # Objets de transfert de données
│   │   │   ├── entity/         # Entités JPA
│   │   │   ├── exception/      # Gestion des exceptions
│   │   │   ├── repository/     # Repositories JPA
│   │   │   ├── security/       # Sécurité et JWT
│   │   │   ├── service/        # Services métier
│   │   │   └── util/           # Utilitaires
│   │   └── resources/
│   │       ├── application.yml # Configuration
│   │       └── static/         # Ressources statiques
│   └── test/                   # Tests unitaires et d'intégration
├── docs/                       # Documentation
├── pom.xml                     # Configuration Maven
└── README.md                   # Ce fichier
```

## Installation et Configuration

### Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- PostgreSQL 12 ou supérieur

### Installation

1. **Cloner le projet**
```bash
git clone <repository-url>
cd school-mgmt-springboot
```

2. **Configurer la base de données**
```bash
sudo -u postgres psql
CREATE DATABASE school_mgmt_niger;
CREATE USER school_user WITH PASSWORD 'school_password';
GRANT ALL PRIVILEGES ON DATABASE school_mgmt_niger TO school_user;
\q
```

3. **Configurer les variables d'environnement**
```bash
export DB_USERNAME=school_user
export DB_PASSWORD=school_password
export JWT_SECRET=your-super-secure-jwt-secret-key-minimum-32-characters
export TWILIO_ACCOUNT_SID=your-twilio-account-sid
export TWILIO_AUTH_TOKEN=your-twilio-auth-token
export TWILIO_PHONE_NUMBER=your-twilio-phone-number
```

4. **Compiler et lancer l'application**
```bash
mvn clean compile
mvn spring-boot:run
```

L'application sera accessible à l'adresse : http://localhost:8080/api

## Documentation API

### Swagger UI
Une fois l'application démarrée, accédez à la documentation interactive :
- **Swagger UI** : http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON** : http://localhost:8080/api/v3/api-docs

### Endpoints Principaux

#### Authentification
- `POST /auth/login` - Connexion utilisateur
- `POST /auth/register` - Inscription utilisateur
- `GET /auth/profile` - Profil utilisateur
- `PUT /auth/profile` - Mise à jour du profil
- `POST /auth/change-password` - Changement de mot de passe

#### Gestion des Étudiants
- `GET /students` - Liste des étudiants
- `POST /students` - Créer un étudiant
- `GET /students/{id}` - Détails d'un étudiant
- `PUT /students/{id}` - Modifier un étudiant
- `DELETE /students/{id}` - Supprimer un étudiant

#### Gestion des Paiements
- `GET /payments` - Liste des paiements
- `POST /payments` - Enregistrer un paiement
- `GET /payments/{id}` - Détails d'un paiement
- `GET /payments/student/{studentId}` - Paiements d'un étudiant
- `GET /payments/{id}/receipt` - Télécharger le reçu PDF

#### Gestion des Notes
- `GET /grades` - Liste des notes
- `POST /grades` - Ajouter une note
- `GET /grades/student/{studentId}` - Notes d'un étudiant
- `GET /grades/student/{studentId}/bulletin` - Bulletin scolaire

#### Notifications
- `GET /notifications` - Liste des notifications
- `POST /notifications` - Créer une notification
- `PUT /notifications/{id}/read` - Marquer comme lue

### Authentification JWT

Toutes les requêtes (sauf login/register) nécessitent un token JWT :
```bash
Authorization: Bearer <your-jwt-token>
```

## Tests

### Exécuter les tests
```bash
# Tests unitaires
mvn test

# Tests avec couverture
mvn test jacoco:report

# Tests d'intégration
mvn verify
```

### Couverture de code
Les rapports de couverture sont générés dans `target/site/jacoco/`

## Déploiement

### Docker
```bash
# Build de l'image
docker build -t school-mgmt-niger .

# Lancement avec Docker Compose
docker-compose up -d
```

### Production
1. Configurer les variables d'environnement de production
2. Utiliser un profil Spring approprié
3. Configurer SSL/TLS
4. Mettre en place la surveillance et les logs

## Configuration Spécifique au Niger

### Système Éducatif
L'application supporte la structure éducative nigérienne :
- **Primaire** : CI, CP, CE1, CE2, CM1, CM2
- **Secondaire 1er cycle** : 6ème, 5ème, 4ème, 3ème
- **Secondaire 2nd cycle** : 2nde, 1ère, Terminale
- **Technique** : CAP, BEP
- **Supérieur** : Licence, Master

### Monnaie et Paiements
- Devise principale : Franc CFA (XOF)
- Support des méthodes de paiement locales
- Intégration Mobile Money (Orange, Airtel, Moov)

### Langues
- Interface en français
- Support multilingue pour les notifications
- Prise en compte des langues locales (Haoussa, Zarma)

## Contribution

### Standards de Code
- Suivre les conventions Java standard
- Utiliser les annotations Spring appropriées
- Documenter les méthodes publiques
- Écrire des tests pour les nouvelles fonctionnalités

### Processus de Contribution
1. Fork le projet
2. Créer une branche feature
3. Commiter les changements
4. Pousser vers la branche
5. Créer une Pull Request

## Support et Maintenance

### Logs
Les logs sont configurés dans `application.yml` :
- Niveau DEBUG pour le développement
- Fichiers de logs dans `logs/school-mgmt.log`

### Monitoring
- Endpoints Actuator activés
- Métriques disponibles via `/actuator/metrics`
- Health check via `/actuator/health`

### Sauvegarde
- Sauvegardes automatiques de la base de données recommandées
- Scripts de sauvegarde disponibles dans `scripts/`

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## Contact

- **Équipe de développement** : dev@ecole.ne
- **Support technique** : support@ecole.ne
- **Site web** : https://ecole.ne

---

*Développé avec ❤️ pour l'éducation au Niger*

