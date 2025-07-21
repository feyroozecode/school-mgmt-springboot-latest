package ne.ecole.schoolmgmt.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ReportCardDto {
    private StudentDto student;
    private String academicYear;
    private String term;
    private BigDecimal generalAverage;
    private String generalAppreciation;
    private List<SubjectAverageDto> subjectAverages;

    // Constructors
    public ReportCardDto() {}

    // Getters and Setters
    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
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

    public BigDecimal getGeneralAverage() {
        return generalAverage;
    }

    public void setGeneralAverage(BigDecimal generalAverage) {
        this.generalAverage = generalAverage;
    }

    public String getGeneralAppreciation() {
        return generalAppreciation;
    }

    public void setGeneralAppreciation(String generalAppreciation) {
        this.generalAppreciation = generalAppreciation;
    }

    public List<SubjectAverageDto> getSubjectAverages() {
        return subjectAverages;
    }

    public void setSubjectAverages(List<SubjectAverageDto> subjectAverages) {
        this.subjectAverages = subjectAverages;
    }

    public static class SubjectAverageDto {
        private String subject;
        private String subjectArabic;
        private BigDecimal average;
        private List<GradeDto> grades;

        public SubjectAverageDto() {}

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

        public BigDecimal getAverage() {
            return average;
        }

        public void setAverage(BigDecimal average) {
            this.average = average;
        }

        public List<GradeDto> getGrades() {
            return grades;
        }

        public void setGrades(List<GradeDto> grades) {
            this.grades = grades;
        }
    }
}

