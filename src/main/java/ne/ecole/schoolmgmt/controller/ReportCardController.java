package ne.ecole.schoolmgmt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ne.ecole.schoolmgmt.dto.ReportCardDto;
import ne.ecole.schoolmgmt.service.PdfReportService;
import ne.ecole.schoolmgmt.service.ReportCardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report-cards")
@Tag(name = "Report Cards", description = "API de gestion des bulletins scolaires")
@CrossOrigin(origins = "*")
public class ReportCardController {

    private final ReportCardService reportCardService;
    private final PdfReportService pdfReportService;

    public ReportCardController(ReportCardService reportCardService, PdfReportService pdfReportService) {
        this.reportCardService = reportCardService;
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Générer un bulletin scolaire", description = "Génère le bulletin scolaire d'un étudiant pour un trimestre et une année académique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bulletin généré avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant ou notes non trouvés"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<ReportCardDto> generateReportCard(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long studentId,
            @Parameter(description = "Trimestre") @RequestParam String term,
            @Parameter(description = "Année académique") @RequestParam String academicYear) {
        
        ReportCardDto reportCard = reportCardService.generateReportCard(studentId, term, academicYear);
        return ResponseEntity.ok(reportCard);
    }

    @GetMapping("/student/{studentId}/pdf")
    @Operation(summary = "Télécharger un bulletin scolaire en PDF", description = "Génère et télécharge le bulletin scolaire d'un étudiant en format PDF avec support franco-arabe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF généré avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant ou notes non trouvés"),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la génération du PDF")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('SECRETARIAT')")
    public ResponseEntity<byte[]> downloadReportCardPdf(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long studentId,
            @Parameter(description = "Trimestre") @RequestParam String term,
            @Parameter(description = "Année académique") @RequestParam String academicYear) {
        
        try {
            ReportCardDto reportCard = reportCardService.generateReportCard(studentId, term, academicYear);
            byte[] pdfBytes = pdfReportService.generateReportCardPdf(reportCard);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", 
                "bulletin_" + reportCard.getStudent().getStudentNumber() + "_" + term + "_" + academicYear + ".pdf");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

