package ne.ecole.schoolmgmt.service;

import ne.ecole.schoolmgmt.dto.CreateStudentRequest;
import ne.ecole.schoolmgmt.dto.StudentDto;
import ne.ecole.schoolmgmt.entity.Student;
import ne.ecole.schoolmgmt.exception.ResourceNotFoundException;
import ne.ecole.schoolmgmt.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public StudentDto createStudent(CreateStudentRequest request) {
        Student student = modelMapper.map(request, Student.class);
        student.setEnrollmentDate(LocalDate.now());
        // Générer un numéro d'étudiant unique si nécessaire
        // student.setStudentNumber(generateUniqueStudentNumber());
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDto.class);
    }

    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(student -> modelMapper.map(student, StudentDto.class));
    }

    @Transactional
    public StudentDto updateStudent(Long id, CreateStudentRequest request) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        
        modelMapper.map(request, existingStudent);
        // Ne pas modifier enrollmentDate ou studentNumber via update request
        
        Student updatedStudent = studentRepository.save(existingStudent);
        return modelMapper.map(updatedStudent, StudentDto.class);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentDto> searchStudents(String query) {
        // Cette méthode peut être améliorée pour inclure la recherche par noms arabes
        return studentRepository.findAll().stream()
                .filter(student -> student.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                                   student.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                                   (student.getFirstNameArabic() != null && student.getFirstNameArabic().toLowerCase().contains(query.toLowerCase())) ||
                                   (student.getLastNameArabic() != null && student.getLastNameArabic().toLowerCase().contains(query.toLowerCase())) ||
                                   (student.getStudentNumber() != null && student.getStudentNumber().toLowerCase().contains(query.toLowerCase())))
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    // Ajoutez d'autres méthodes de service selon les besoins (ex: statistiques, rapports)
}


