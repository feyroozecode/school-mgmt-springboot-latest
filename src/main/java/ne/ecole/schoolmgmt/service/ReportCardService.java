package ne.ecole.schoolmgmt.service;

import ne.ecole.schoolmgmt.dto.GradeDto;
import ne.ecole.schoolmgmt.dto.ReportCardDto;
import ne.ecole.schoolmgmt.dto.StudentDto;
import ne.ecole.schoolmgmt.entity.Grade;
import ne.ecole.schoolmgmt.entity.Student;
import ne.ecole.schoolmgmt.exception.ResourceNotFoundException;
import ne.ecole.schoolmgmt.repository.GradeRepository;
import ne.ecole.schoolmgmt.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportCardService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public ReportCardService(GradeRepository gradeRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public ReportCardDto generateReportCard(Long studentId, String term, String academicYear) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        List<Grade> grades = gradeRepository.findByStudentIdAndTermAndAcademicYear(studentId, term, academicYear);

        if (grades.isEmpty()) {
            throw new ResourceNotFoundException("No grades found for student " + studentId + " in term " + term + " of academic year " + academicYear);
        }

        ReportCardDto reportCard = new ReportCardDto();
        reportCard.setStudent(modelMapper.map(student, StudentDto.class));
        reportCard.setAcademicYear(academicYear);
        reportCard.setTerm(term);

        // Grouper les notes par matière
        Map<String, List<Grade>> gradesBySubject = grades.stream()
                .collect(Collectors.groupingBy(Grade::getSubject));

        List<ReportCardDto.SubjectAverageDto> subjectAverages = new ArrayList<>();
        BigDecimal totalWeightedAverage = BigDecimal.ZERO;
        BigDecimal totalCoefficient = BigDecimal.ZERO;

        for (Map.Entry<String, List<Grade>> entry : gradesBySubject.entrySet()) {
            String subject = entry.getKey();
            List<Grade> subjectGrades = entry.getValue();

            ReportCardDto.SubjectAverageDto subjectAverage = new ReportCardDto.SubjectAverageDto();
            subjectAverage.setSubject(subject);

            // Récupérer le nom arabe de la matière (du premier grade de cette matière)
            String subjectArabic = subjectGrades.get(0).getSubjectArabic();
            subjectAverage.setSubjectArabic(subjectArabic);

            // Calculer la moyenne de la matière
            BigDecimal subjectTotal = BigDecimal.ZERO;
            BigDecimal subjectCoeffTotal = BigDecimal.ZERO;

            List<GradeDto> gradeDtos = new ArrayList<>();
            for (Grade grade : subjectGrades) {
                BigDecimal coefficient = grade.getCoefficient() != null ? grade.getCoefficient() : BigDecimal.ONE;
                subjectTotal = subjectTotal.add(grade.getScore().multiply(coefficient));
                subjectCoeffTotal = subjectCoeffTotal.add(coefficient);

                GradeDto gradeDto = modelMapper.map(grade, GradeDto.class);
                gradeDto.setStudentId(studentId);
                gradeDto.setStudentName(student.getFullName());
                if (student.getFirstNameArabic() != null && student.getLastNameArabic() != null) {
                    gradeDto.setStudentNameArabic(student.getFirstNameArabic() + " " + student.getLastNameArabic());
                }
                gradeDtos.add(gradeDto);
            }

            BigDecimal subjectAverageValue = subjectCoeffTotal.compareTo(BigDecimal.ZERO) > 0 ?
                    subjectTotal.divide(subjectCoeffTotal, 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

            subjectAverage.setAverage(subjectAverageValue);
            subjectAverage.setGrades(gradeDtos);
            subjectAverages.add(subjectAverage);

            // Contribuer à la moyenne générale
            totalWeightedAverage = totalWeightedAverage.add(subjectAverageValue.multiply(subjectCoeffTotal));
            totalCoefficient = totalCoefficient.add(subjectCoeffTotal);
        }

        reportCard.setSubjectAverages(subjectAverages);

        // Calculer la moyenne générale
        BigDecimal generalAverage = totalCoefficient.compareTo(BigDecimal.ZERO) > 0 ?
                totalWeightedAverage.divide(totalCoefficient, 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        reportCard.setGeneralAverage(generalAverage);

        // Déterminer l'appréciation générale
        reportCard.setGeneralAppreciation(getAppreciation(generalAverage));

        return reportCard;
    }

    private String getAppreciation(BigDecimal average) {
        if (average.compareTo(BigDecimal.valueOf(16)) >= 0) {
            return "Très bien";
        } else if (average.compareTo(BigDecimal.valueOf(14)) >= 0) {
            return "Bien";
        } else if (average.compareTo(BigDecimal.valueOf(12)) >= 0) {
            return "Assez bien";
        } else if (average.compareTo(BigDecimal.valueOf(10)) >= 0) {
            return "Passable";
        } else {
            return "Insuffisant";
        }
    }
}

