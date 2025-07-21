package ne.ecole.schoolmgmt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import ne.ecole.schoolmgmt.entity.EducationLevel;
import ne.ecole.schoolmgmt.entity.Gender;
import ne.ecole.schoolmgmt.entity.PrimaryScript;

import java.time.LocalDate;

public class CreateStudentRequest {
    
    // Noms français (obligatoires)
    @NotBlank(message = "Le prénom français est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom français doit contenir entre 2 et 50 caractères")
    private String firstName;
    
    @NotBlank(message = "Le nom français est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom français doit contenir entre 2 et 50 caractères")
    private String lastName;
    
    // Noms arabes (optionnels)
    @Size(min = 2, max = 100, message = "Le prénom arabe doit contenir entre 2 et 100 caractères")
    private String firstNameArabic;
    
    @Size(min = 2, max = 100, message = "Le nom arabe doit contenir entre 2 et 100 caractères")
    private String lastNameArabic;
    
    // Préférences linguistiques
    private String preferredLanguage = "fr";
    private PrimaryScript primaryScript = PrimaryScript.LATIN;
    
    // Informations personnelles
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate birthDate;
    
    @NotNull(message = "Le genre est obligatoire")
    private Gender gender;
    
    private String birthPlace;
    private String nationality = "Nigérienne";
    
    // Informations académiques
    @NotNull(message = "Le niveau d'éducation est obligatoire")
    private EducationLevel educationLevel;
    
    @NotBlank(message = "Le nom de la classe est obligatoire")
    private String className;
    
    private String classNameArabic;
    
    // Informations familiales
    private String fatherName;
    private String fatherPhone;
    private String fatherProfession;
    private String motherName;
    private String motherPhone;
    private String motherProfession;
    private String guardianName;
    private String guardianPhone;
    private String guardianRelationship;
    
    // Adresse
    private String address;
    private String city;
    private String region;
    
    // Informations médicales
    private String medicalConditions;
    private String allergies;
    
    // Langues parlées
    private String languagesSpoken;
    
    // Constructors
    public CreateStudentRequest() {}
    
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
}

