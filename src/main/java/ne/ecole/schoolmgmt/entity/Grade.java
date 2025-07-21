package ne.ecole.schoolmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "grades")
public class Grade extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "La matière est obligatoire")
    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "subject_arabic", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String subjectArabic;

    @NotNull(message = "La note est obligatoire")
    @DecimalMin(value = "0.0", message = "La note doit être positive")
    @DecimalMax(value = "20.0", message = "La note ne peut pas dépasser 20")
    @Column(name = "score", nullable = false, precision = 4, scale = 2)
    private BigDecimal score;

    @Column(name = "max_score", nullable = false, precision = 4, scale = 2)
    private BigDecimal maxScore = BigDecimal.valueOf(20); // Sur 20 par défaut

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_type", nullable = false)
    private GradeType gradeType;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "academic_year", nullable = false)
    private String academicYear; // Format: "2024-2025"

    @Column(name = "term", nullable = false)
    private String term; // Trimestre

    @Column(name = "coefficient", precision = 3, scale = 1)
    private BigDecimal coefficient = BigDecimal.ONE;

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    // Constructors
    public Grade() {}

    public Grade(Student student, String subject, BigDecimal score, GradeType gradeType, 
                String academicYear, String term) {
        this.student = student;
        this.subject = subject;
        this.score = score;
        this.gradeType = gradeType;
        this.academicYear = academicYear;
        this.term = term;
        this.examDate = LocalDate.now();
    }

    // Getters and Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
}

