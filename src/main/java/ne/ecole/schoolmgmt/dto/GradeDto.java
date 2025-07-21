package ne.ecole.schoolmgmt.dto;

import ne.ecole.schoolmgmt.entity.GradeType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GradeDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentNameArabic;
    
    // Matière
    private String subject;
    private String subjectArabic;
    
    // Note
    private BigDecimal score;
    private BigDecimal maxScore;
    private GradeType gradeType;
    
    // Informations contextuelles
    private LocalDate examDate;
    private String academicYear;
    private String term;
    private BigDecimal coefficient;
    private String teacherName;
    private String comments;
    
    // Constructors
    public GradeDto() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getStudentNameArabic() {
        return studentNameArabic;
    }
    
    public void setStudentNameArabic(String studentNameArabic) {
        this.studentNameArabic = studentNameArabic;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getSubjectArabic() {
        return subjectArabic;
    }
    
    public void setSubjectArabic(String subjectArabic) {
        this.subjectArabic = subjectArabic;
    }
    
    public BigDecimal getScore() {
        return score;
    }
    
    public void setScore(BigDecimal score) {
        this.score = score;
    }
    
    public BigDecimal getMaxScore() {
        return maxScore;
    }
    
    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }
    
    public GradeType getGradeType() {
        return gradeType;
    }
    
    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }
    
    public LocalDate getExamDate() {
        return examDate;
    }
    
    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
    public String getTerm() {
        return term;
    }
    
    public void setTerm(String term) {
        this.term = term;
    }
    
    public BigDecimal getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
    
    public String getTeacherName() {
        return teacherName;
    }
    
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    // Méthodes utilitaires
    public BigDecimal getPercentage() {
        if (maxScore.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return score.divide(maxScore, 2, java.math.RoundingMode.HALF_UP)
                   .multiply(BigDecimal.valueOf(100));
    }
    
    public String getLetterGrade() {
        BigDecimal percentage = getPercentage();
        if (percentage.compareTo(BigDecimal.valueOf(90)) >= 0) return "A";
        if (percentage.compareTo(BigDecimal.valueOf(80)) >= 0) return "B";
        if (percentage.compareTo(BigDecimal.valueOf(70)) >= 0) return "C";
        if (percentage.compareTo(BigDecimal.valueOf(60)) >= 0) return "D";
        if (percentage.compareTo(BigDecimal.valueOf(50)) >= 0) return "E";
        return "F";
    }
    
    public String getDisplaySubject(String language) {
        if ("ar".equals(language) && subjectArabic != null) {
            return subjectArabic;
        }
        return subject;
    }
    
    public String getDisplayStudentName(String language) {
        if ("ar".equals(language) && studentNameArabic != null) {
            return studentNameArabic;
        }
        return studentName;
    }
}

