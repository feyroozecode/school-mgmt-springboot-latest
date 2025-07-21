package ne.ecole.schoolmgmt.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import ne.ecole.schoolmgmt.dto.ReportCardDto;
import ne.ecole.schoolmgmt.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfReportService {

    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
    private static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
    private static final Font ARABIC_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

    public byte[] generateReportCardPdf(ReportCardDto reportCard) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        // En-tête du bulletin
        addHeader(document, reportCard);

        // Informations de l'étudiant
        addStudentInfo(document, reportCard.getStudent());

        // Tableau des notes
        addGradesTable(document, reportCard);

        // Moyennes et appréciations
        addAveragesAndAppreciations(document, reportCard);

        // Pied de page
        addFooter(document);

        document.close();
        return baos.toByteArray();
    }

    private void addHeader(Document document, ReportCardDto reportCard) throws DocumentException {
        // Titre principal
        Paragraph title = new Paragraph("BULLETIN SCOLAIRE", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Titre en arabe si l'étudiant préfère l'arabe
        if ("ar".equals(reportCard.getStudent().getPreferredLanguage())) {
            Paragraph titleArabic = new Paragraph("كشف النقاط المدرسي", ARABIC_FONT);
            titleArabic.setAlignment(Element.ALIGN_CENTER);
            titleArabic.setSpacingAfter(20);
            document.add(titleArabic);
        }

        // Informations de période
        PdfPTable periodTable = new PdfPTable(2);
        periodTable.setWidthPercentage(100);
        periodTable.setSpacingAfter(20);

        periodTable.addCell(new PdfPCell(new Phrase("Année scolaire: " + reportCard.getAcademicYear(), HEADER_FONT)));
        periodTable.addCell(new PdfPCell(new Phrase("Trimestre: " + reportCard.getTerm(), HEADER_FONT)));

        document.add(periodTable);
    }

    private void addStudentInfo(Document document, StudentDto student) throws DocumentException {
        PdfPTable studentTable = new PdfPTable(2);
        studentTable.setWidthPercentage(100);
        studentTable.setSpacingAfter(20);

        // Nom français
        studentTable.addCell(new PdfPCell(new Phrase("Nom: " + student.getFullName(), NORMAL_FONT)));
        studentTable.addCell(new PdfPCell(new Phrase("Numéro d'étudiant: " + student.getStudentNumber(), NORMAL_FONT)));

        // Nom arabe si disponible
        if (student.getFullNameArabic() != null) {
            studentTable.addCell(new PdfPCell(new Phrase("الاسم: " + student.getFullNameArabic(), ARABIC_FONT)));
            studentTable.addCell(new PdfPCell(new Phrase(""))); // Cellule vide pour l'alignement
        }

        // Classe
        studentTable.addCell(new PdfPCell(new Phrase("Classe: " + student.getClassName(), NORMAL_FONT)));
        if (student.getClassNameArabic() != null) {
            studentTable.addCell(new PdfPCell(new Phrase("القسم: " + student.getClassNameArabic(), ARABIC_FONT)));
        } else {
            studentTable.addCell(new PdfPCell(new Phrase("")));
        }

        // Date de naissance
        if (student.getBirthDate() != null) {
            studentTable.addCell(new PdfPCell(new Phrase("Date de naissance: " + 
                student.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), NORMAL_FONT)));
            studentTable.addCell(new PdfPCell(new Phrase("")));
        }

        document.add(studentTable);
    }

    private void addGradesTable(Document document, ReportCardDto reportCard) throws DocumentException {
        PdfPTable gradesTable = new PdfPTable(4);
        gradesTable.setWidthPercentage(100);
        gradesTable.setSpacingAfter(20);

        // En-têtes du tableau
        PdfPCell headerCell1 = new PdfPCell(new Phrase("Matière", HEADER_FONT));
        headerCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        gradesTable.addCell(headerCell1);

        if ("ar".equals(reportCard.getStudent().getPreferredLanguage())) {
            PdfPCell headerCell2 = new PdfPCell(new Phrase("المادة", HEADER_FONT));
            headerCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            gradesTable.addCell(headerCell2);
        } else {
            gradesTable.addCell(new PdfPCell(new Phrase("", HEADER_FONT)));
        }

        PdfPCell headerCell3 = new PdfPCell(new Phrase("Moyenne", HEADER_FONT));
        headerCell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        gradesTable.addCell(headerCell3);

        PdfPCell headerCell4 = new PdfPCell(new Phrase("Appréciation", HEADER_FONT));
        headerCell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        gradesTable.addCell(headerCell4);

        // Lignes des matières
        for (ReportCardDto.SubjectAverageDto subjectAverage : reportCard.getSubjectAverages()) {
            gradesTable.addCell(new PdfPCell(new Phrase(subjectAverage.getSubject(), NORMAL_FONT)));
            
            if ("ar".equals(reportCard.getStudent().getPreferredLanguage()) && subjectAverage.getSubjectArabic() != null) {
                gradesTable.addCell(new PdfPCell(new Phrase(subjectAverage.getSubjectArabic(), ARABIC_FONT)));
            } else {
                gradesTable.addCell(new PdfPCell(new Phrase("", NORMAL_FONT)));
            }

            gradesTable.addCell(new PdfPCell(new Phrase(subjectAverage.getAverage().toString() + "/20", NORMAL_FONT)));
            gradesTable.addCell(new PdfPCell(new Phrase(getAppreciation(subjectAverage.getAverage()), NORMAL_FONT)));
        }

        document.add(gradesTable);
    }

    private void addAveragesAndAppreciations(Document document, ReportCardDto reportCard) throws DocumentException {
        // Moyenne générale
        PdfPTable averageTable = new PdfPTable(2);
        averageTable.setWidthPercentage(100);
        averageTable.setSpacingAfter(20);

        PdfPCell avgCell = new PdfPCell(new Phrase("Moyenne générale: " + reportCard.getGeneralAverage() + "/20", HEADER_FONT));
        avgCell.setBackgroundColor(BaseColor.YELLOW);
        averageTable.addCell(avgCell);

        PdfPCell appCell = new PdfPCell(new Phrase("Appréciation: " + reportCard.getGeneralAppreciation(), HEADER_FONT));
        appCell.setBackgroundColor(BaseColor.YELLOW);
        averageTable.addCell(appCell);

        document.add(averageTable);
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("Bulletin généré le " + 
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), NORMAL_FONT);
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setSpacingBefore(30);
        document.add(footer);

        // Signature
        Paragraph signature = new Paragraph("Signature du directeur: ____________________", NORMAL_FONT);
        signature.setAlignment(Element.ALIGN_RIGHT);
        signature.setSpacingBefore(30);
        document.add(signature);
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

