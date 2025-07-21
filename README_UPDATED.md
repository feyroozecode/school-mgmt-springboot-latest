# Système de Gestion Scolaire Franco-Arabe

## 🌟 Vue d'ensemble

Ce système de gestion scolaire moderne est spécialement conçu pour les établissements scolaires du Niger et d'autres pays francophones avec des communautés arabophones. Il offre un support complet pour la gestion bilingue français-arabe, permettant aux écoles franco-arabes de gérer efficacement leurs étudiants, notes, et bulletins scolaires.

## ✨ Fonctionnalités principales

### 🎓 Gestion des étudiants
- **Profils bilingues** : Noms en français et en arabe
- **Informations complètes** : Données personnelles, familiales, médicales
- **Système de classes** : Support des niveaux du système éducatif nigérien
- **Recherche multilingue** : Recherche par nom français ou arabe

### 📊 Gestion des notes
- **Matières bilingues** : Noms des matières en français et en arabe
- **Système de notation** : Notes sur 20 avec coefficients
- **Types d'évaluations** : Devoirs, interrogations, examens, projets
- **Calcul automatique** : Moyennes pondérées par matière et générale

### 📋 Bulletins scolaires
- **Génération automatique** : Bulletins complets par trimestre
- **Support bilingue** : Affichage adaptatif selon la langue préférée
- **Export PDF** : Bulletins professionnels avec support RTL pour l'arabe
- **Appréciations** : Commentaires automatiques basés sur les moyennes

### 🔐 Authentification et sécurité
- **JWT Authentication** : Système d'authentification sécurisé
- **Gestion des rôles** : Admin, Professeur, Secrétariat, Finances
- **Autorisation granulaire** : Contrôle d'accès par fonctionnalité

## 🛠️ Technologies utilisées

- **Backend** : Spring Boot 3.x, Java 17
- **Base de données** : PostgreSQL 15
- **Sécurité** : Spring Security, JWT
- **Documentation** : Swagger/OpenAPI 3
- **PDF** : iText pour la génération de bulletins
- **Containerisation** : Docker & Docker Compose
- **Tests** : JUnit 5, Mockito

## 🚀 Installation et démarrage

### Prérequis
- Docker et Docker Compose installés
- Java 17+ (pour le développement local)
- Maven 3.6+ (pour le développement local)

### Démarrage rapide avec Docker

1. **Cloner le projet**
   ```bash
   git clone <url-du-repo>
   cd school-mgmt-springboot
   ```

2. **Lancer l'application**
   ```bash
   ./launch.sh
   ```

   Le script automatisé va :
   - Construire l'image Docker
   - Démarrer PostgreSQL et l'application
   - Vérifier la santé de l'application
   - Afficher les URLs d'accès

3. **Accéder à l'application**
   - API : http://localhost:8080
   - Documentation : http://localhost:8080/swagger-ui.html
   - Base de données : localhost:5432

### Démarrage manuel

1. **Démarrer PostgreSQL**
   ```bash
   docker run -d --name postgres \
     -e POSTGRES_DB=school_mgmt \
     -e POSTGRES_USER=school_user \
     -e POSTGRES_PASSWORD=school_password \
     -p 5432:5432 postgres:15-alpine
   ```

2. **Configurer l'application**
   ```bash
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/school_mgmt
   export SPRING_DATASOURCE_USERNAME=school_user
   export SPRING_DATASOURCE_PASSWORD=school_password
   ```

3. **Compiler et lancer**
   ```bash
   mvn clean install
   java -jar target/school-mgmt-springboot-0.0.1-SNAPSHOT.jar
   ```

## 📚 Utilisation

### Authentification

1. **Créer un compte administrateur** (première utilisation)
   ```bash
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "email": "admin@ecole.ne",
       "password": "motdepasse123",
       "firstName": "Admin",
       "lastName": "Système",
       "role": "ADMIN"
     }'
   ```

2. **Se connecter**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "email": "admin@ecole.ne",
       "password": "motdepasse123"
     }'
   ```

### Gestion des étudiants franco-arabes

**Créer un étudiant bilingue :**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "firstName": "Ahmed",
    "lastName": "Moussa",
    "firstNameArabic": "أحمد",
    "lastNameArabic": "موسى",
    "preferredLanguage": "ar",
    "primaryScript": "ARABIC",
    "birthDate": "2010-05-15",
    "gender": "MALE",
    "educationLevel": "PRIMARY",
    "className": "CM2",
    "classNameArabic": "الصف الخامس",
    "fatherName": "Ibrahim Moussa",
    "fatherPhone": "+227 90 12 34 56",
    "address": "Quartier Plateau, Niamey",
    "city": "Niamey",
    "languagesSpoken": "Français, Arabe, Haoussa"
  }'
```

### Gestion des notes

**Ajouter une note bilingue :**
```bash
curl -X POST http://localhost:8080/api/grades \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "studentId": 1,
    "subject": "Mathématiques",
    "subjectArabic": "الرياضيات",
    "score": 15.5,
    "maxScore": 20,
    "gradeType": "EXAM",
    "academicYear": "2023-2024",
    "term": "Trimestre 2",
    "coefficient": 2
  }'
```

### Génération de bulletins

**Télécharger un bulletin PDF :**
```bash
curl -X GET "http://localhost:8080/api/report-cards/student/1/pdf?term=Trimestre%201&academicYear=2023-2024" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -o bulletin.pdf
```

## 🏗️ Architecture

```
school-mgmt-springboot/
├── src/
│   ├── main/
│   │   ├── java/ne/ecole/schoolmgmt/
│   │   │   ├── controller/          # Contrôleurs REST
│   │   │   ├── service/             # Services métier
│   │   │   ├── repository/          # Repositories JPA
│   │   │   ├── entity/              # Entités JPA
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── config/              # Configuration Spring
│   │   │   ├── security/            # Sécurité JWT
│   │   │   └── exception/           # Gestion des exceptions
│   │   └── resources/
│   │       ├── application.yml      # Configuration
│   │       └── static/              # Ressources statiques
│   └── test/                        # Tests unitaires
├── docs/                            # Documentation
├── Dockerfile                       # Image Docker
├── docker-compose.yml              # Orchestration
├── launch.sh                       # Script de lancement
└── pom.xml                         # Dépendances Maven
```

## 🌍 Spécificités Niger et Franco-Arabes

### Système éducatif nigérien
- **Préscolaire** : Petite, Moyenne, Grande Section
- **Primaire** : CI, CP, CE1, CE2, CM1, CM2
- **Collège** : 6ème, 5ème, 4ème, 3ème
- **Lycée** : 2nde, 1ère, Terminale

### Support linguistique
- **Français** : Langue officielle d'enseignement
- **Arabe** : Support complet pour les écoles franco-arabes
- **Langues locales** : Champ pour Haoussa, Zarma, etc.

### Fonctionnalités culturelles
- **Noms bilingues** : Respect des traditions de nommage
- **Calendrier scolaire** : Adapté au contexte local
- **Bulletins adaptatifs** : Format selon la langue préférée
- **Support RTL** : Écriture arabe de droite à gauche

## 🧪 Tests

**Lancer les tests :**
```bash
mvn test
```

**Tests avec couverture :**
```bash
mvn test jacoco:report
```

## 📖 Documentation API

La documentation interactive Swagger est disponible à :
http://localhost:8080/swagger-ui.html

Documentation détaillée : [API_SPECIFICATION_UPDATED.md](docs/API_SPECIFICATION_UPDATED.md)

## 🔧 Configuration

### Variables d'environnement

| Variable | Description | Défaut |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | URL de la base de données | `jdbc:postgresql://localhost:5432/school_mgmt` |
| `SPRING_DATASOURCE_USERNAME` | Utilisateur DB | `school_user` |
| `SPRING_DATASOURCE_PASSWORD` | Mot de passe DB | `school_password` |
| `JWT_SECRET` | Clé secrète JWT | `mySecretKey123...` |
| `JWT_EXPIRATION` | Durée du token (ms) | `86400000` (24h) |

### Profils Spring

- **default** : Développement local
- **docker** : Conteneur Docker
- **prod** : Production

## 🚀 Déploiement

### Docker Compose (Recommandé)
```bash
docker-compose up -d
```

### Kubernetes
```bash
# Fichiers de déploiement K8s disponibles sur demande
kubectl apply -f k8s/
```

### Cloud (AWS, Azure, GCP)
- Support des bases de données managées
- Configuration via variables d'environnement
- Scaling horizontal supporté

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -am 'Ajouter nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Créer une Pull Request

## 📝 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 📞 Support

Pour toute question ou support :
- Email : support@ecole.ne
- Documentation : [docs/](docs/)
- Issues : GitHub Issues

## 🎯 Roadmap

- [ ] Interface web React/Angular
- [ ] Application mobile (React Native/Flutter)
- [ ] Intégration SMS (Twilio)
- [ ] Système de paiement mobile money
- [ ] Rapports avancés et analytics
- [ ] Support multilingue étendu (Haoussa, Zarma)
- [ ] Intégration avec systèmes gouvernementaux

---

**Développé avec ❤️ pour l'éducation au Niger et en Afrique francophone**

