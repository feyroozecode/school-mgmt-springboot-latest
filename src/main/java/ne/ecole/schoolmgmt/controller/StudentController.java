package ne.ecole.schoolmgmt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ne.ecole.schoolmgmt.dto.CreateStudentRequest;
import ne.ecole.schoolmgmt.dto.StudentDto;
import ne.ecole.schoolmgmt.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "API de gestion des étudiants")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant", description = "Crée un nouvel étudiant avec support franco-arabe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIAT')")
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        StudentDto createdStudent = studentService.createStudent(request);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un étudiant par ID", description = "Récupère les détails d'un étudiant spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIAT') or hasRole('PROFESSEUR')")
    public ResponseEntity<StudentDto> getStudentById(@Parameter(description = "ID de l'étudiant") @PathVariable Long id) {
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    @Operation(summary = "Lister tous les étudiants", description = "Récupère la liste paginée de tous les étudiants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIAT') or hasRole('PROFESSEUR')")
    public ResponseEntity<Page<StudentDto>> getAllStudents(Pageable pageable) {
        Page<StudentDto> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un étudiant", description = "Met à jour les informations d'un étudiant existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Étudiant mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIAT')")
    public ResponseEntity<StudentDto> updateStudent(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long id,
            @Valid @RequestBody CreateStudentRequest request) {
        StudentDto updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant", description = "Supprime un étudiant du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@Parameter(description = "ID de l'étudiant") @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des étudiants", description = "Recherche des étudiants par nom (français ou arabe) ou numéro d'étudiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIAT') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<StudentDto>> searchStudents(
            @Parameter(description = "Terme de recherche") @RequestParam String query) {
        List<StudentDto> students = studentService.searchStudents(query);
        return ResponseEntity.ok(students);
    }
}

