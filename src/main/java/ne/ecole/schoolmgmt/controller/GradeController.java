package ne.ecole.schoolmgmt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ne.ecole.schoolmgmt.dto.GradeDto;
import ne.ecole.schoolmgmt.service.GradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Tag(name = "Grades", description = "API de gestion des notes")
@CrossOrigin(origins = "*")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle note", description = "Crée une nouvelle note pour un étudiant avec support franco-arabe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<GradeDto> createGrade(@Valid @RequestBody GradeDto gradeDto) {
        GradeDto createdGrade = gradeService.createGrade(gradeDto);
        return new ResponseEntity<>(createdGrade, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une note par ID", description = "Récupère les détails d'une note spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note trouvée"),
            @ApiResponse(responseCode = "404", description = "Note non trouvée")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<GradeDto> getGradeById(@Parameter(description = "ID de la note") @PathVariable Long id) {
        GradeDto grade = gradeService.getGradeById(id);
        return ResponseEntity.ok(grade);
    }

    @GetMapping
    @Operation(summary = "Lister toutes les notes", description = "Récupère la liste paginée de toutes les notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des notes récupérée")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<Page<GradeDto>> getAllGrades(Pageable pageable) {
        Page<GradeDto> grades = gradeService.getAllGrades(pageable);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Obtenir les notes d'un étudiant", description = "Récupère toutes les notes d'un étudiant spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes de l'étudiant récupérées"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<List<GradeDto>> getGradesByStudentId(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long studentId) {
        List<GradeDto> grades = gradeService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/student/{studentId}/term")
    @Operation(summary = "Obtenir les notes d'un étudiant par trimestre", description = "Récupère les notes d'un étudiant pour un trimestre et une année académique spécifiques")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes du trimestre récupérées"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<List<GradeDto>> getGradesByStudentAndTerm(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long studentId,
            @Parameter(description = "Trimestre") @RequestParam String term,
            @Parameter(description = "Année académique") @RequestParam String academicYear) {
        List<GradeDto> grades = gradeService.getGradesByStudentAndTerm(studentId, term, academicYear);
        return ResponseEntity.ok(grades);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une note", description = "Met à jour les informations d'une note existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Note non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<GradeDto> updateGrade(
            @Parameter(description = "ID de la note") @PathVariable Long id,
            @Valid @RequestBody GradeDto gradeDto) {
        GradeDto updatedGrade = gradeService.updateGrade(id, gradeDto);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une note", description = "Supprime une note du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Note non trouvée")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<Void> deleteGrade(@Parameter(description = "ID de la note") @PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/average")
    @Operation(summary = "Calculer la moyenne d'un étudiant", description = "Calcule la moyenne pondérée d'un étudiant pour un trimestre et une année académique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moyenne calculée avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<BigDecimal> calculateStudentAverage(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long studentId,
            @Parameter(description = "Trimestre") @RequestParam String term,
            @Parameter(description = "Année académique") @RequestParam String academicYear) {
        BigDecimal average = gradeService.calculateStudentAverage(studentId, term, academicYear);
        return ResponseEntity.ok(average);
    }
}

