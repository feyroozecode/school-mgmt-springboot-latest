# Spécification API - Système de Gestion Scolaire Franco-Arabe

## Vue d'ensemble

Cette API REST permet la gestion complète d'un système scolaire avec support bilingue français-arabe, spécialement conçu pour les écoles du Niger et d'autres pays francophones avec communautés arabophones.

## URL de base
```
http://localhost:8080/api
```

## Authentification

L'API utilise l'authentification JWT (JSON Web Token). Incluez le token dans l'en-tête Authorization :
```
Authorization: Bearer <votre-token-jwt>
```

## Endpoints d'authentification

### POST /auth/login
Connexion utilisateur

**Corps de la requête :**
```json
{
  "email": "admin@ecole.ne",
  "password": "motdepasse"
}
```

**Réponse :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "admin@ecole.ne",
    "firstName": "Admin",
    "lastName": "Système",
    "role": "ADMIN"
  }
}
```

### POST /auth/register
Inscription d'un nouvel utilisateur

**Corps de la requête :**
```json
{
  "email": "nouveau@ecole.ne",
  "password": "motdepasse",
  "firstName": "Nouveau",
  "lastName": "Utilisateur",
  "role": "PROFESSEUR"
}
```

## Endpoints des étudiants

### POST /students
Créer un nouvel étudiant avec support franco-arabe

**Corps de la requête :**
```json
{
  "firstName": "Ahmed",
  "lastName": "Moussa",
  "firstNameArabic": "أحمد",
  "lastNameArabic": "موسى",
  "preferredLanguage": "ar",
  "primaryScript": "ARABIC",
  "birthDate": "2010-05-15",
  "gender": "MALE",
  "birthPlace": "Niamey",
  "nationality": "Nigérienne",
  "educationLevel": "PRIMARY",
  "className": "CM2",
  "classNameArabic": "الصف الخامس",
  "fatherName": "Ibrahim Moussa",
  "fatherPhone": "+227 90 12 34 56",
  "motherName": "Fatima Ali",
  "motherPhone": "+227 90 12 34 57",
  "address": "Quartier Plateau, Niamey",
  "city": "Niamey",
  "region": "Niamey",
  "languagesSpoken": "Français, Arabe, Haoussa"
}
```

**Réponse :**
```json
{
  "id": 1,
  "firstName": "Ahmed",
  "lastName": "Moussa",
  "firstNameArabic": "أحمد",
  "lastNameArabic": "موسى",
  "preferredLanguage": "ar",
  "primaryScript": "ARABIC",
  "birthDate": "2010-05-15",
  "gender": "MALE",
  "className": "CM2",
  "classNameArabic": "الصف الخامس",
  "studentNumber": "STU001",
  "enrollmentDate": "2024-01-15",
  "status": "ACTIVE"
}
```

### GET /students/{id}
Obtenir un étudiant par ID

**Réponse :**
```json
{
  "id": 1,
  "firstName": "Ahmed",
  "lastName": "Moussa",
  "firstNameArabic": "أحمد",
  "lastNameArabic": "موسى",
  "displayName": "أحمد موسى",
  "displayClassName": "الصف الخامس",
  "preferredLanguage": "ar",
  "primaryScript": "ARABIC"
}
```

### GET /students
Lister tous les étudiants (paginé)

**Paramètres de requête :**
- `page` : Numéro de page (défaut: 0)
- `size` : Taille de page (défaut: 20)
- `sort` : Critère de tri (ex: firstName,asc)

### PUT /students/{id}
Mettre à jour un étudiant

### DELETE /students/{id}
Supprimer un étudiant

### GET /students/search?query={terme}
Rechercher des étudiants par nom (français ou arabe) ou numéro d'étudiant

## Endpoints des notes

### POST /grades
Créer une nouvelle note avec support franco-arabe

**Corps de la requête :**
```json
{
  "studentId": 1,
  "subject": "Mathématiques",
  "subjectArabic": "الرياضيات",
  "score": 15.5,
  "maxScore": 20,
  "gradeType": "EXAM",
  "examDate": "2024-03-15",
  "academicYear": "2023-2024",
  "term": "Trimestre 2",
  "coefficient": 2,
  "teacherName": "M. Diallo",
  "comments": "Bon travail"
}
```

### GET /grades/student/{studentId}
Obtenir toutes les notes d'un étudiant

### GET /grades/student/{studentId}/term?term={trimestre}&academicYear={annee}
Obtenir les notes d'un étudiant pour un trimestre spécifique

### GET /grades/student/{studentId}/average?term={trimestre}&academicYear={annee}
Calculer la moyenne d'un étudiant pour un trimestre

## Endpoints des bulletins scolaires

### GET /report-cards/student/{studentId}?term={trimestre}&academicYear={annee}
Générer un bulletin scolaire

**Réponse :**
```json
{
  "student": {
    "id": 1,
    "firstName": "Ahmed",
    "lastName": "Moussa",
    "firstNameArabic": "أحمد",
    "lastNameArabic": "موسى",
    "className": "CM2",
    "classNameArabic": "الصف الخامس",
    "preferredLanguage": "ar"
  },
  "academicYear": "2023-2024",
  "term": "Trimestre 2",
  "generalAverage": 14.25,
  "generalAppreciation": "Bien",
  "subjectAverages": [
    {
      "subject": "Mathématiques",
      "subjectArabic": "الرياضيات",
      "average": 15.5,
      "grades": [...]
    }
  ]
}
```

### GET /report-cards/student/{studentId}/pdf?term={trimestre}&academicYear={annee}
Télécharger un bulletin scolaire en PDF bilingue

**Réponse :** Fichier PDF avec support RTL pour l'arabe

## Codes d'erreur

- `200` : Succès
- `201` : Créé avec succès
- `400` : Requête invalide
- `401` : Non authentifié
- `403` : Accès refusé
- `404` : Ressource non trouvée
- `500` : Erreur serveur

## Énumérations

### EducationLevel
- `PRESCHOOL` : Préscolaire
- `PRIMARY` : Primaire (CI, CP, CE1, CE2, CM1, CM2)
- `MIDDLE_SCHOOL` : Collège (6ème, 5ème, 4ème, 3ème)
- `HIGH_SCHOOL` : Lycée (2nde, 1ère, Terminale)
- `UNIVERSITY` : Université
- `VOCATIONAL` : Formation professionnelle

### Gender
- `MALE` : Masculin
- `FEMALE` : Féminin

### PrimaryScript
- `LATIN` : Écriture latine (français)
- `ARABIC` : Écriture arabe

### GradeType
- `HOMEWORK` : Devoir
- `QUIZ` : Interrogation
- `EXAM` : Examen
- `PROJECT` : Projet
- `PARTICIPATION` : Participation

### UserRole
- `ADMIN` : Administrateur
- `FINANCES` : Responsable finances
- `PROFESSEUR` : Professeur
- `SECRETARIAT` : Secrétariat

## Fonctionnalités spéciales franco-arabes

1. **Noms bilingues** : Chaque étudiant peut avoir un nom en français et en arabe
2. **Matières bilingues** : Les matières peuvent être nommées en français et en arabe
3. **Bulletins adaptatifs** : Les bulletins s'adaptent à la langue préférée de l'étudiant
4. **Support RTL** : Les PDF supportent l'écriture de droite à gauche pour l'arabe
5. **Recherche multilingue** : La recherche fonctionne en français et en arabe

## Exemples d'utilisation

### Créer un étudiant franco-arabe
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "firstName": "Youssef",
    "lastName": "Ibrahim",
    "firstNameArabic": "يوسف",
    "lastNameArabic": "إبراهيم",
    "preferredLanguage": "ar",
    "primaryScript": "ARABIC",
    "birthDate": "2011-08-20",
    "gender": "MALE",
    "educationLevel": "PRIMARY",
    "className": "CE2",
    "classNameArabic": "الصف الثالث"
  }'
```

### Télécharger un bulletin en PDF
```bash
curl -X GET "http://localhost:8080/api/report-cards/student/1/pdf?term=Trimestre%201&academicYear=2023-2024" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -o bulletin.pdf
```

