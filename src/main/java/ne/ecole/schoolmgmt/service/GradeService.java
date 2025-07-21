package ne.ecole.schoolmgmt.service;

import ne.ecole.schoolmgmt.dto.GradeDto;
import ne.ecole.schoolmgmt.entity.Grade;
import ne.ecole.schoolmgmt.entity.Student;
import ne.ecole.schoolmgmt.exception.ResourceNotFoundException;
import ne.ecole.schoolmgmt.repository.GradeRepository;
import ne.ecole.schoolmgmt.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public GradeDto createGrade(GradeDto gradeDto) {
        Student student = studentRepository.findById(gradeDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + gradeDto.getStudentId()));
        
        Grade grade = modelMapper.map(gradeDto, Grade.class);
        grade.setStudent(student);
        
        Grade savedGrade = gradeRepository.save(grade);
        return convertToDto(savedGrade);
    }

    @Transactional(readOnly = true)
    public GradeDto getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id " + id));
        return convertToDto(grade);
    }

    @Transactional(readOnly = true)
    public Page<GradeDto> getAllGrades(Pageable pageable) {
        return gradeRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<GradeDto> getGradesByStudentId(Long studentId) {
        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        return grades.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GradeDto> getGradesByStudentAndTerm(Long studentId, String term, String academicYear) {
        List<Grade> grades = gradeRepository.findByStudentIdAndTermAndAcademicYear(studentId, term, academicYear);
        return grades.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GradeDto updateGrade(Long id, GradeDto gradeDto) {
        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id " + id));
        
        // Mise à jour des champs modifiables
        existingGrade.setSubject(gradeDto.getSubject());
        existingGrade.setSubjectArabic(gradeDto.getSubjectArabic());
        existingGrade.setScore(gradeDto.getScore());
        existingGrade.setMaxScore(gradeDto.getMaxScore());
        existingGrade.setGradeType(gradeDto.getGradeType());
        existingGrade.setExamDate(gradeDto.getExamDate());
        existingGrade.setTerm(gradeDto.getTerm());
        existingGrade.setCoefficient(gradeDto.getCoefficient());
        existingGrade.setTeacherName(gradeDto.getTeacherName());
        existingGrade.setComments(gradeDto.getComments());
        
        Grade updatedGrade = gradeRepository.save(existingGrade);
        return convertToDto(updatedGrade);
    }

    @Transactional
    public void deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grade not found with id " + id);
        }
        gradeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateStudentAverage(Long studentId, String term, String academicYear) {
        List<Grade> grades = gradeRepository.findByStudentIdAndTermAndAcademicYear(studentId, term, academicYear);
        
        if (grades.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalWeightedScore = BigDecimal.ZERO;
        BigDecimal totalCoefficient = BigDecimal.ZERO;
        
        for (Grade grade : grades) {
            BigDecimal coefficient = grade.getCoefficient() != null ? grade.getCoefficient() : BigDecimal.ONE;
            totalWeightedScore = totalWeightedScore.add(grade.getScore().multiply(coefficient));
            totalCoefficient = totalCoefficient.add(coefficient);
        }
        
        if (totalCoefficient.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalWeightedScore.divide(totalCoefficient, 2, java.math.RoundingMode.HALF_UP);
    }

    private GradeDto convertToDto(Grade grade) {
        GradeDto dto = modelMapper.map(grade, GradeDto.class);
        dto.setStudentId(grade.getStudent().getId());
        dto.setStudentName(grade.getStudent().getFullName());
        
        // Ajouter le nom arabe si disponible
        if (grade.getStudent().getFirstNameArabic() != null && grade.getStudent().getLastNameArabic() != null) {
            dto.setStudentNameArabic(grade.getStudent().getFirstNameArabic() + " " + grade.getStudent().getLastNameArabic());
        }
        
        return dto;
    }
}

