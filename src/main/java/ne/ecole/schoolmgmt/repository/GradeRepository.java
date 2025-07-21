package ne.ecole.schoolmgmt.repository;

import ne.ecole.schoolmgmt.entity.Grade;
import ne.ecole.schoolmgmt.entity.GradeType;
import ne.ecole.schoolmgmt.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudent(Student student);

    List<Grade> findByStudentId(Long studentId);

    List<Grade> findByStudentAndAcademicYear(Student student, String academicYear);

    List<Grade> findByStudentAndAcademicYearAndTerm(Student student, String academicYear, String term);

    List<Grade> findByStudentAndSubject(Student student, String subject);

    List<Grade> findByAcademicYear(String academicYear);

    List<Grade> findByAcademicYearAndTerm(String academicYear, String term);

    List<Grade> findBySubject(String subject);

    List<Grade> findByGradeType(GradeType gradeType);

    Page<Grade> findByStudent(Student student, Pageable pageable);

    Page<Grade> findByAcademicYear(String academicYear, Pageable pageable);

    Page<Grade> findBySubject(String subject, Pageable pageable);

    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId AND g.academicYear = :academicYear AND g.term = :term")
    List<Grade> findByStudentIdAndAcademicYearAndTerm(@Param("studentId") Long studentId,
                                                     @Param("academicYear") String academicYear,
                                                     @Param("term") String term);

    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId AND g.term = :term AND g.academicYear = :academicYear")
    List<Grade> findByStudentIdAndTermAndAcademicYear(@Param("studentId") Long studentId,
                                                     @Param("term") String term,
                                                     @Param("academicYear") String academicYear);

    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student = :student AND g.academicYear = :academicYear")
    BigDecimal getAverageScoreByStudentAndYear(@Param("student") Student student, 
                                              @Param("academicYear") String academicYear);

    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student = :student AND g.academicYear = :academicYear AND g.term = :term")
    BigDecimal getAverageScoreByStudentYearAndTerm(@Param("student") Student student, 
                                                  @Param("academicYear") String academicYear,
                                                  @Param("term") String term);

    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.subject = :subject AND g.academicYear = :academicYear")
    BigDecimal getAverageScoreBySubjectAndYear(@Param("subject") String subject, 
                                              @Param("academicYear") String academicYear);

    @Query("SELECT g.subject, AVG(g.score) FROM Grade g WHERE g.student = :student AND g.academicYear = :academicYear GROUP BY g.subject")
    List<Object[]> getAverageScoresBySubjectForStudent(@Param("student") Student student, 
                                                      @Param("academicYear") String academicYear);

    @Query("SELECT g.subject, AVG(g.score) FROM Grade g WHERE g.academicYear = :academicYear GROUP BY g.subject")
    List<Object[]> getAverageScoresBySubject(@Param("academicYear") String academicYear);

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.score >= :minScore AND g.academicYear = :academicYear")
    long countGradesAboveScore(@Param("minScore") BigDecimal minScore, 
                              @Param("academicYear") String academicYear);

    @Query("SELECT DISTINCT g.subject FROM Grade g WHERE g.academicYear = :academicYear ORDER BY g.subject")
    List<String> findDistinctSubjectsByAcademicYear(@Param("academicYear") String academicYear);

    @Query("SELECT DISTINCT g.academicYear FROM Grade g ORDER BY g.academicYear DESC")
    List<String> findDistinctAcademicYears();

    @Query("SELECT DISTINCT g.term FROM Grade g WHERE g.academicYear = :academicYear ORDER BY g.term")
    List<String> findDistinctTermsByAcademicYear(@Param("academicYear") String academicYear);

    @Query("SELECT g FROM Grade g WHERE g.student.className = :className AND g.academicYear = :academicYear AND g.term = :term")
    List<Grade> findByClassNameAndAcademicYearAndTerm(@Param("className") String className,
                                                     @Param("academicYear") String academicYear,
                                                     @Param("term") String term);
}

