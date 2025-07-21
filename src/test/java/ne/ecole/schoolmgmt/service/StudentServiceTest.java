package ne.ecole.schoolmgmt.service;

import ne.ecole.schoolmgmt.dto.CreateStudentRequest;
import ne.ecole.schoolmgmt.dto.StudentDto;
import ne.ecole.schoolmgmt.entity.EducationLevel;
import ne.ecole.schoolmgmt.entity.Gender;
import ne.ecole.schoolmgmt.entity.PrimaryScript;
import ne.ecole.schoolmgmt.entity.Student;
import ne.ecole.schoolmgmt.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentDto studentDto;
    private CreateStudentRequest createStudentRequest;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Jean");
        student.setLastName("Dupont");
        student.setFirstNameArabic("جان");
        student.setLastNameArabic("دوبون");
        student.setPreferredLanguage("fr");
        student.setPrimaryScript(PrimaryScript.LATIN);
        student.setBirthDate(LocalDate.of(2010, 5, 15));
        student.setGender(Gender.MASCULIN);
        student.setClassName("CM2");
        student.setClassNameArabic("الصف الخامس");
        student.setEducationLevel(EducationLevel.CI);

        studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setFirstName("Jean");
        studentDto.setLastName("Dupont");
        studentDto.setFirstNameArabic("جان");
        studentDto.setLastNameArabic("دوبون");
        studentDto.setPreferredLanguage("fr");
        studentDto.setPrimaryScript(PrimaryScript.LATIN);
        studentDto.setBirthDate(LocalDate.of(2010, 5, 15));
        studentDto.setGender(Gender.MASCULIN);
        studentDto.setClassName("CM2");
        studentDto.setClassNameArabic("الصف الخامس");
        studentDto.setEducationLevel(EducationLevel.CP);

        createStudentRequest = new CreateStudentRequest();
        createStudentRequest.setFirstName("Jean");
        createStudentRequest.setLastName("Dupont");
        createStudentRequest.setFirstNameArabic("جان");
        createStudentRequest.setLastNameArabic("دوبون");
        createStudentRequest.setPreferredLanguage("fr");
        createStudentRequest.setPrimaryScript(PrimaryScript.LATIN);
        createStudentRequest.setBirthDate(LocalDate.of(2010, 5, 15));
        createStudentRequest.setGender(Gender.MASCULIN);
        createStudentRequest.setClassName("CM2");
        createStudentRequest.setClassNameArabic("الصف الخامس");
        createStudentRequest.setEducationLevel(EducationLevel.CP);
    }

    @Test
    void testCreateStudent() {
        when(modelMapper.map(any(CreateStudentRequest.class), any(Class.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(any(Student.class), any(Class.class))).thenReturn(studentDto);

        StudentDto result = studentService.createStudent(createStudentRequest);

        assertNotNull(result);
        assertEquals(studentDto.getFirstName(), result.getFirstName());
        assertEquals(studentDto.getFirstNameArabic(), result.getFirstNameArabic());
        assertEquals(studentDto.getClassNameArabic(), result.getClassNameArabic());
    }

    @Test
    void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(modelMapper.map(any(Student.class), any(Class.class))).thenReturn(studentDto);

        StudentDto result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(studentDto.getId(), result.getId());
        assertEquals(studentDto.getLastNameArabic(), result.getLastNameArabic());
    }

    @Test
    void testUpdateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(any(CreateStudentRequest.class), any(Class.class))).thenReturn(student);
        when(modelMapper.map(any(Student.class), any(Class.class))).thenReturn(studentDto);

        StudentDto result = studentService.updateStudent(1L, createStudentRequest);

        assertNotNull(result);
        assertEquals(studentDto.getFirstName(), result.getFirstName());
        assertEquals(studentDto.getFirstNameArabic(), result.getFirstNameArabic());
        assertEquals(studentDto.getClassNameArabic(), result.getClassNameArabic());
    }

    @Test
    void testSearchStudents() {
        // Cette méthode de test est plus complexe car la méthode searchStudents filtre en mémoire.
        // Pour un test unitaire, on peut simuler le comportement de findAll et le filtrage.
        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Ali");
        student2.setLastName("Diallo");
        student2.setFirstNameArabic("علي");
        student2.setLastNameArabic("ديالو");
        student2.setStudentNumber("S002");

        when(studentRepository.findAll()).thenReturn(java.util.Arrays.asList(student, student2));
        when(modelMapper.map(student, StudentDto.class)).thenReturn(studentDto);
        when(modelMapper.map(student2, StudentDto.class)).thenReturn(modelMapper.map(student2, StudentDto.class)); // Simuler le mapping pour student2

        List<StudentDto> results = studentService.searchStudents("jean");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Jean", results.get(0).getFirstName());

        results = studentService.searchStudents("علي");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("علي", results.get(0).getFirstNameArabic());

        results = studentService.searchStudents("S002");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Ali", results.get(0).getFirstName());
    }
}


