# Spécification API - Système de Gestion Scolaire Niger

## Introduction

Cette documentation présente la spécification complète de l'API REST du Système de Gestion Scolaire Niger. L'API est construite avec Spring Boot et suit les principes RESTful pour offrir une interface cohérente et intuitive.

## Informations Générales

- **URL de base** : `http://localhost:8080/api` (développement)
- **URL de production** : `https://api.ecole.ne/api`
- **Version** : 1.0.0
- **Format de données** : JSON
- **Authentification** : JWT Bearer Token
- **Encodage** : UTF-8

## Authentification

### Vue d'ensemble
L'API utilise l'authentification JWT (JSON Web Token). Après une connexion réussie, un token est fourni et doit être inclus dans l'en-tête `Authorization` de toutes les requêtes protégées.

### Format du token
```
Authorization: Bearer <jwt-token>
```

### Durée de vie
- **Expiration** : 7 jours
- **Renouvellement** : Connexion requise après expiration

## Codes de Statut HTTP

| Code | Description |
|------|-------------|
| 200 | Succès |
| 201 | Créé avec succès |
| 400 | Requête invalide |
| 401 | Non authentifié |
| 403 | Accès refusé |
| 404 | Ressource non trouvée |
| 409 | Conflit (ressource existante) |
| 422 | Erreur de validation |
| 500 | Erreur serveur interne |

## Format des Erreurs

```json
{
  "status": 400,
  "message": "Description de l'erreur",
  "path": "/api/endpoint",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Erreurs de Validation
```json
{
  "status": 400,
  "message": "Erreurs de validation",
  "errors": {
    "firstName": "Le prénom est obligatoire",
    "email": "Format d'email invalide"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

## Endpoints d'Authentification

### POST /auth/login
Authentifie un utilisateur et retourne un token JWT.

**Requête :**
```json
{
  "email": "admin@ecole.ne",
  "password": "motdepasse123"
}
```

**Réponse :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "user": {
    "id": 1,
    "firstName": "Admin",
    "lastName": "Système",
    "email": "admin@ecole.ne",
    "role": "ADMIN",
    "isActive": true,
    "phone": "+227 90 12 34 56",
    "createdAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
}
```

### POST /auth/register
Crée un nouveau compte utilisateur.

**Requête :**
```json
{
  "firstName": "Jean",
  "lastName": "Dupont",
  "email": "jean.dupont@ecole.ne",
  "password": "motdepasse123",
  "role": "PROFESSEUR",
  "phone": "+227 90 12 34 56"
}
```

**Réponse :** Identique à `/auth/login`

### GET /auth/profile
Récupère le profil de l'utilisateur connecté.

**Headers requis :**
```
Authorization: Bearer <token>
```

**Réponse :**
```json
{
  "id": 1,
  "firstName": "Admin",
  "lastName": "Système",
  "email": "admin@ecole.ne",
  "role": "ADMIN",
  "isActive": true,
  "phone": "+227 90 12 34 56",
  "createdAt": "2024-01-01T00:00:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### PUT /auth/profile
Met à jour le profil de l'utilisateur connecté.

**Requête :**
```json
{
  "firstName": "Admin",
  "lastName": "Principal",
  "phone": "+227 90 12 34 57"
}
```

**Réponse :** Profil utilisateur mis à jour

### POST /auth/change-password
Change le mot de passe de l'utilisateur connecté.

**Requête :**
```json
{
  "currentPassword": "ancienMotDePasse",
  "newPassword": "nouveauMotDePasse123"
}
```

**Réponse :** `200 OK` (pas de contenu)

## Endpoints de Gestion des Étudiants

### GET /students
Récupère la liste des étudiants avec pagination.

**Paramètres de requête :**
- `page` (optionnel) : Numéro de page (défaut: 0)
- `size` (optionnel) : Taille de page (défaut: 20)
- `search` (optionnel) : Recherche par nom ou numéro étudiant
- `status` (optionnel) : Filtrer par statut (ACTIVE, SUSPENDED, etc.)
- `educationLevel` (optionnel) : Filtrer par niveau d'éducation
- `className` (optionnel) : Filtrer par classe

**Réponse :**
```json
{
  "content": [
    {
      "id": 1,
      "firstName": "Aminata",
      "lastName": "Diallo",
      "birthDate": "2010-05-15",
      "gender": "FEMININ",
      "birthPlace": "Niamey",
      "nationality": "Nigérienne",
      "educationLevel": "CM2",
      "className": "CM2-A",
      "studentNumber": "2024001",
      "enrollmentDate": "2024-09-01",
      "status": "ACTIVE",
      "fatherName": "Ibrahim Diallo",
      "fatherPhone": "+227 90 11 22 33",
      "motherName": "Fatouma Diallo",
      "motherPhone": "+227 90 44 55 66",
      "address": "Quartier Plateau, Niamey",
      "city": "Niamey",
      "region": "Niamey",
      "languagesSpoken": "Français, Haoussa, Zarma",
      "createdAt": "2024-01-01T00:00:00",
      "updatedAt": "2024-01-15T10:30:00"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true
    },
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 150,
  "totalPages": 8,
  "first": true,
  "last": false,
  "numberOfElements": 20
}
```

### POST /students
Crée un nouveau profil étudiant.

**Requête :**
```json
{
  "firstName": "Moussa",
  "lastName": "Oumarou",
  "birthDate": "2008-03-20",
  "gender": "MASCULIN",
  "birthPlace": "Zinder",
  "nationality": "Nigérienne",
  "educationLevel": "TROISIEME",
  "className": "3ème-B",
  "enrollmentDate": "2024-09-01",
  "fatherName": "Oumarou Moussa",
  "fatherPhone": "+227 90 77 88 99",
  "fatherProfession": "Commerçant",
  "motherName": "Aïcha Oumarou",
  "motherPhone": "+227 90 11 22 33",
  "motherProfession": "Couturière",
  "address": "Quartier Sabon Gari, Zinder",
  "city": "Zinder",
  "region": "Zinder",
  "languagesSpoken": "Français, Haoussa"
}
```

**Réponse :** Profil étudiant créé avec `id` généré et `studentNumber` automatique

### GET /students/{id}
Récupère les détails d'un étudiant spécifique.

**Réponse :** Objet étudiant complet

### PUT /students/{id}
Met à jour les informations d'un étudiant.

**Requête :** Objet étudiant avec champs à modifier

**Réponse :** Étudiant mis à jour

### DELETE /students/{id}
Supprime un étudiant (soft delete - change le statut).

**Réponse :** `200 OK`

### GET /students/{id}/payments
Récupère l'historique des paiements d'un étudiant.

**Réponse :**
```json
[
  {
    "id": 1,
    "amount": 25000.00,
    "paymentType": "FRAIS_SCOLARITE",
    "paymentMethod": "ORANGE_MONEY",
    "paymentDate": "2024-01-15",
    "academicYear": "2024-2025",
    "term": "1er Trimestre",
    "receiptNumber": "REC-2024-001",
    "status": "COMPLETED",
    "currency": "XOF",
    "collectedBy": "Secrétariat"
  }
]
```

### GET /students/{id}/grades
Récupère les notes d'un étudiant.

**Paramètres de requête :**
- `academicYear` (optionnel) : Année académique
- `term` (optionnel) : Trimestre
- `subject` (optionnel) : Matière

**Réponse :**
```json
[
  {
    "id": 1,
    "subject": "Mathématiques",
    "score": 15.5,
    "maxScore": 20.0,
    "gradeType": "COMPOSITION",
    "examDate": "2024-01-10",
    "academicYear": "2024-2025",
    "term": "1er Trimestre",
    "coefficient": 2.0,
    "teacherName": "M. Abdou",
    "comments": "Bon travail, continue ainsi",
    "percentage": 77.5,
    "letterGrade": "B"
  }
]
```

## Endpoints de Gestion des Paiements

### GET /payments
Liste tous les paiements avec pagination et filtres.

**Paramètres de requête :**
- `page`, `size` : Pagination
- `studentId` : Filtrer par étudiant
- `paymentType` : Type de paiement
- `status` : Statut du paiement
- `academicYear` : Année académique
- `startDate`, `endDate` : Période de paiement

### POST /payments
Enregistre un nouveau paiement.

**Requête :**
```json
{
  "studentId": 1,
  "amount": 25000.00,
  "paymentType": "FRAIS_SCOLARITE",
  "paymentMethod": "ORANGE_MONEY",
  "academicYear": "2024-2025",
  "term": "1er Trimestre",
  "notes": "Paiement des frais du 1er trimestre",
  "collectedBy": "Mme Fatima - Secrétariat"
}
```

**Réponse :** Paiement créé avec `receiptNumber` généré automatiquement

### GET /payments/{id}
Détails d'un paiement spécifique.

### GET /payments/{id}/receipt
Télécharge le reçu PDF du paiement.

**Réponse :** Fichier PDF

### GET /payments/statistics
Statistiques des paiements.

**Paramètres de requête :**
- `academicYear` : Année académique
- `startDate`, `endDate` : Période

**Réponse :**
```json
{
  "totalAmount": 2500000.00,
  "totalPayments": 150,
  "paymentsByType": {
    "FRAIS_SCOLARITE": 2000000.00,
    "FRAIS_INSCRIPTION": 300000.00,
    "FRAIS_EXAMEN": 200000.00
  },
  "paymentsByMethod": {
    "ORANGE_MONEY": 1200000.00,
    "ESPECES": 800000.00,
    "AIRTEL_MONEY": 500000.00
  },
  "paymentsByMonth": [
    {"month": "2024-01", "amount": 500000.00},
    {"month": "2024-02", "amount": 600000.00}
  ]
}
```

## Endpoints de Gestion des Notes

### GET /grades
Liste des notes avec filtres.

**Paramètres de requête :**
- `studentId` : Notes d'un étudiant
- `subject` : Notes d'une matière
- `academicYear` : Année académique
- `term` : Trimestre
- `gradeType` : Type de note

### POST /grades
Ajoute une nouvelle note.

**Requête :**
```json
{
  "studentId": 1,
  "subject": "Histoire-Géographie",
  "score": 14.0,
  "maxScore": 20.0,
  "gradeType": "DEVOIR",
  "examDate": "2024-01-20",
  "academicYear": "2024-2025",
  "term": "1er Trimestre",
  "coefficient": 1.5,
  "teacherName": "Mme Aïcha",
  "comments": "Effort à poursuivre"
}
```

### PUT /grades/{id}
Modifie une note existante.

### DELETE /grades/{id}
Supprime une note.

### GET /grades/student/{studentId}/bulletin
Génère le bulletin scolaire d'un étudiant.

**Paramètres de requête :**
- `academicYear` : Année académique (requis)
- `term` : Trimestre (requis)
- `format` : Format de sortie (json, pdf)

**Réponse (JSON) :**
```json
{
  "student": {
    "id": 1,
    "firstName": "Aminata",
    "lastName": "Diallo",
    "className": "CM2-A"
  },
  "academicYear": "2024-2025",
  "term": "1er Trimestre",
  "grades": [
    {
      "subject": "Mathématiques",
      "averageScore": 15.5,
      "coefficient": 2.0,
      "weightedScore": 31.0,
      "letterGrade": "B",
      "gradeCount": 3
    }
  ],
  "overallAverage": 14.2,
  "totalCoefficient": 12.0,
  "rank": 5,
  "totalStudents": 35,
  "appreciation": "Bon travail, continue tes efforts"
}
```

### GET /grades/statistics
Statistiques des notes par classe, matière, etc.

## Endpoints de Notifications

### GET /notifications
Liste des notifications de l'utilisateur connecté.

**Paramètres de requête :**
- `status` : Statut (PENDING, SENT, READ)
- `type` : Type de notification
- `unreadOnly` : Seulement les non lues (true/false)

### POST /notifications
Crée une nouvelle notification.

**Requête :**
```json
{
  "title": "Rappel de paiement",
  "message": "Cher parent, les frais de scolarité du 2ème trimestre sont dus avant le 15 février.",
  "type": "PAYMENT_REMINDER",
  "priority": "HIGH",
  "recipientPhone": "+227 90 11 22 33",
  "channel": "SMS",
  "scheduledFor": "2024-02-10T09:00:00",
  "language": "fr"
}
```

### PUT /notifications/{id}/read
Marque une notification comme lue.

### GET /notifications/statistics
Statistiques des notifications (envoyées, lues, échecs).

## Endpoints d'Administration

### GET /admin/dashboard
Tableau de bord avec statistiques générales.

**Réponse :**
```json
{
  "totalStudents": 450,
  "activeStudents": 425,
  "totalPayments": 2500000.00,
  "pendingPayments": 150000.00,
  "averageGrade": 13.5,
  "notificationsSent": 1250,
  "studentsByLevel": {
    "PRIMAIRE": 200,
    "SECONDAIRE": 200,
    "TECHNIQUE": 50
  },
  "recentActivities": [
    {
      "type": "STUDENT_ENROLLED",
      "description": "Nouvel étudiant inscrit: Moussa Oumarou",
      "timestamp": "2024-01-15T10:30:00"
    }
  ]
}
```

### GET /admin/users
Gestion des utilisateurs (ADMIN uniquement).

### POST /admin/users
Créer un nouvel utilisateur.

### PUT /admin/users/{id}/status
Activer/désactiver un utilisateur.

## Utilitaires

### GET /health
Vérification de l'état de l'application.

**Réponse :**
```json
{
  "status": "UP",
  "application": "Système de Gestion Scolaire - Niger",
  "version": "1.0.0",
  "timestamp": "2024-01-15T10:30:00",
  "message": "Application fonctionnelle"
}
```

### GET /actuator/health
Health check détaillé (Spring Actuator).

### GET /actuator/metrics
Métriques de l'application.

## Modèles de Données

### Utilisateur (User)
```json
{
  "id": "number",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "role": "ADMIN|FINANCES|PROFESSEUR|SECRETARIAT",
  "isActive": "boolean",
  "phone": "string",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Étudiant (Student)
```json
{
  "id": "number",
  "firstName": "string",
  "lastName": "string",
  "birthDate": "date",
  "gender": "MASCULIN|FEMININ",
  "birthPlace": "string",
  "nationality": "string",
  "educationLevel": "CI|CP|CE1|CE2|CM1|CM2|SIXIEME|...",
  "className": "string",
  "studentNumber": "string",
  "enrollmentDate": "date",
  "status": "ACTIVE|SUSPENDED|GRADUATED|TRANSFERRED|DROPPED_OUT",
  "fatherName": "string",
  "fatherPhone": "string",
  "fatherProfession": "string",
  "motherName": "string",
  "motherPhone": "string",
  "motherProfession": "string",
  "guardianName": "string",
  "guardianPhone": "string",
  "guardianRelationship": "string",
  "address": "string",
  "city": "string",
  "region": "string",
  "medicalConditions": "string",
  "allergies": "string",
  "languagesSpoken": "string",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Paiement (Payment)
```json
{
  "id": "number",
  "studentId": "number",
  "amount": "decimal",
  "paymentType": "FRAIS_SCOLARITE|FRAIS_INSCRIPTION|...",
  "paymentMethod": "ESPECES|ORANGE_MONEY|AIRTEL_MONEY|...",
  "paymentDate": "date",
  "academicYear": "string",
  "term": "string",
  "receiptNumber": "string",
  "status": "PENDING|COMPLETED|CANCELLED|REFUNDED|PARTIAL",
  "notes": "string",
  "collectedBy": "string",
  "currency": "string",
  "exchangeRate": "decimal",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Note (Grade)
```json
{
  "id": "number",
  "studentId": "number",
  "subject": "string",
  "score": "decimal",
  "maxScore": "decimal",
  "gradeType": "DEVOIR|INTERROGATION|COMPOSITION|...",
  "examDate": "date",
  "academicYear": "string",
  "term": "string",
  "coefficient": "decimal",
  "teacherName": "string",
  "comments": "string",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Notification
```json
{
  "id": "number",
  "title": "string",
  "message": "string",
  "type": "PAYMENT_REMINDER|GRADE_PUBLISHED|...",
  "priority": "LOW|NORMAL|HIGH|URGENT",
  "recipientId": "number",
  "recipientPhone": "string",
  "recipientEmail": "string",
  "channel": "SMS|EMAIL|IN_APP|PUSH|WHATSAPP",
  "status": "PENDING|SENT|DELIVERED|READ|FAILED|CANCELLED",
  "sentAt": "datetime",
  "readAt": "datetime",
  "errorMessage": "string",
  "retryCount": "number",
  "scheduledFor": "datetime",
  "language": "string",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

## Codes d'Erreur Spécifiques

| Code | Message | Description |
|------|---------|-------------|
| AUTH_001 | Email ou mot de passe incorrect | Échec d'authentification |
| AUTH_002 | Token JWT expiré | Token d'authentification expiré |
| AUTH_003 | Token JWT invalide | Token malformé ou corrompu |
| USER_001 | Email déjà utilisé | Tentative de création avec email existant |
| USER_002 | Utilisateur non trouvé | ID utilisateur inexistant |
| STUDENT_001 | Numéro étudiant déjà utilisé | Numéro d'étudiant en doublon |
| STUDENT_002 | Étudiant non trouvé | ID étudiant inexistant |
| PAYMENT_001 | Montant invalide | Montant négatif ou nul |
| PAYMENT_002 | Paiement non trouvé | ID paiement inexistant |
| GRADE_001 | Note hors limites | Note supérieure au maximum autorisé |
| GRADE_002 | Note non trouvée | ID note inexistant |
| NOTIF_001 | Destinataire requis | Notification sans destinataire |
| NOTIF_002 | Canal non supporté | Canal de notification invalide |

## Limites et Quotas

- **Taille maximale de requête** : 10 MB
- **Timeout de requête** : 30 secondes
- **Pagination maximale** : 100 éléments par page
- **Requêtes par minute** : 1000 (par utilisateur authentifié)
- **Taille maximale de fichier** : 5 MB (PDF, images)

## Exemples d'Utilisation

### Inscription d'un nouvel étudiant avec paiement
```bash
# 1. Créer l'étudiant
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Fatima",
    "lastName": "Moussa",
    "birthDate": "2009-07-12",
    "gender": "FEMININ",
    "educationLevel": "CINQUIEME",
    "className": "5ème-A",
    "enrollmentDate": "2024-09-01",
    "fatherName": "Moussa Ibrahim",
    "fatherPhone": "+227 90 12 34 56"
  }'

# 2. Enregistrer le paiement d'inscription
curl -X POST http://localhost:8080/api/payments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 123,
    "amount": 15000.00,
    "paymentType": "FRAIS_INSCRIPTION",
    "paymentMethod": "ORANGE_MONEY",
    "academicYear": "2024-2025"
  }'
```

### Consultation du bulletin d'un étudiant
```bash
curl -X GET "http://localhost:8080/api/grades/student/123/bulletin?academicYear=2024-2025&term=1er%20Trimestre" \
  -H "Authorization: Bearer $TOKEN"
```

### Envoi d'une notification de rappel
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Réunion de parents",
    "message": "Réunion de parents prévue le samedi 20 janvier à 9h en salle de conférence.",
    "type": "PARENT_MEETING",
    "priority": "HIGH",
    "recipientPhone": "+227 90 12 34 56",
    "channel": "SMS",
    "language": "fr"
  }'
```

## Changelog

### Version 1.0.0 (2024-01-15)
- Version initiale de l'API
- Authentification JWT
- Gestion complète des étudiants
- Système de paiements avec support CFA
- Gestion des notes et bulletins
- Notifications multi-canal
- Documentation OpenAPI/Swagger

---

*Cette documentation est maintenue à jour avec chaque version de l'API. Pour les dernières modifications, consultez le changelog ou la documentation Swagger interactive.*

