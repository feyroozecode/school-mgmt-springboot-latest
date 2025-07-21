package ne.ecole.schoolmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student extends BaseEntity {

    // Noms français
    @Size(min = 2, max = 50, message = "Le prénom français doit contenir entre 2 et 50 caractères")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 50, message = "Le nom français doit contenir entre 2 et 50 caractères")
    @Column(name = "last_name")
    private String lastName;

    // Noms arabes
    @Size(min = 2, max = 100, message = "Le prénom arabe doit contenir entre 2 et 100 caractères")
    @Column(name = "first_name_arabic", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String firstNameArabic;

    @Size(min = 2, max = 100, message = "Le nom arabe doit contenir entre 2 et 100 caractères")
    @Column(name = "last_name_arabic", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String lastNameArabic;

    // Préférences linguistiques
    @Column(name = "preferred_language", nullable = false)
    private String preferredLanguage = "fr"; // fr, ar

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_script", nullable = false)
    private PrimaryScript primaryScript = PrimaryScript.LATIN;

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "nationality", nullable = false)
    private String nationality = "Nigérienne";

    // Informations académiques
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false)
    private EducationLevel educationLevel;

    @Column(name = "class_name")
    private String className;

    @Column(name = "class_name_arabic", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String classNameArabic;

    @Column(name = "student_number", unique = true)
    private String studentNumber;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StudentStatus status = StudentStatus.ACTIVE;

    // Informations familiales
    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "father_phone")
    private String fatherPhone;

    @Column(name = "father_profession")
    private String fatherProfession;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "mother_phone")
    private String motherPhone;

    @Column(name = "mother_profession")
    private String motherProfession;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_phone")
    private String guardianPhone;

    @Column(name = "guardian_relationship")
    private String guardianRelationship;

    // Adresse
    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    // Informations médicales
    @Column(name = "medical_conditions", columnDefinition = "TEXT")
    private String medicalConditions;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    // Langues parlées (spécifique au Niger)
    @Column(name = "languages_spoken")
    private String languagesSpoken; // Français, Haoussa, Zarma, etc.

    // Constructors
    public Student() {}

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameArabic() {
        return firstNameArabic;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public String getLastNameArabic() {
        return lastNameArabic;
    }

    public void setLastNameArabic(String lastNameArabic) {
        this.lastNameArabic = lastNameArabic;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public PrimaryScript getPrimaryScript() {
        return primaryScript;
    }

    public void setPrimaryScript(PrimaryScript primaryScript) {
        this.primaryScript = primaryScript;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameArabic() {
        return classNameArabic;
    }

    public void setClassNameArabic(String classNameArabic) {
        this.classNameArabic = classNameArabic;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getFatherProfession() {
        return fatherProfession;
    }

    public void setFatherProfession(String fatherProfession) {
        this.fatherProfession = fatherProfession;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public String getMotherProfession() {
        return motherProfession;
    }

    public void setMotherProfession(String motherProfession) {
        this.motherProfession = motherProfession;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getGuardianRelationship() {
        return guardianRelationship;
    }

    public void setGuardianRelationship(String guardianRelationship) {
        this.guardianRelationship = guardianRelationship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(String languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}

