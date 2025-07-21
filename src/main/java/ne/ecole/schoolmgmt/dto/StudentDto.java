package ne.ecole.schoolmgmt.dto;

import ne.ecole.schoolmgmt.entity.EducationLevel;
import ne.ecole.schoolmgmt.entity.Gender;
import ne.ecole.schoolmgmt.entity.PrimaryScript;
import ne.ecole.schoolmgmt.entity.StudentStatus;

import java.time.LocalDate;

public class StudentDto {
    private Long id;
    
    // Noms français
    private String firstName;
    private String lastName;
    
    // Noms arabes
    private String firstNameArabic;
    private String lastNameArabic;
    
    // Préférences linguistiques
    private String preferredLanguage;
    private PrimaryScript primaryScript;
    
    // Informations personnelles
    private LocalDate birthDate;
    private Gender gender;
    private String birthPlace;
    private String nationality;
    
    // Informations académiques
    private EducationLevel educationLevel;
    private String className;
    private String classNameArabic;
    private String studentNumber;
    private LocalDate enrollmentDate;
    private StudentStatus status;
    
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
    public StudentDto() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    // Méthodes utilitaires
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getFullNameArabic() {
        if (firstNameArabic != null && lastNameArabic != null) {
            return firstNameArabic + " " + lastNameArabic;
        }
        return null;
    }
    
    public String getDisplayName() {
        if ("ar".equals(preferredLanguage) && getFullNameArabic() != null) {
            return getFullNameArabic();
        }
        return getFullName();
    }
    
    public String getDisplayClassName() {
        if ("ar".equals(preferredLanguage) && classNameArabic != null) {
            return classNameArabic;
        }
        return className;
    }
}

