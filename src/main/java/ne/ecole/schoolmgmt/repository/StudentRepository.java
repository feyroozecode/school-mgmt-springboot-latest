package ne.ecole.schoolmgmt.repository;

import ne.ecole.schoolmgmt.entity.Student;
import ne.ecole.schoolmgmt.entity.StudentStatus;
import ne.ecole.schoolmgmt.entity.EducationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentNumber(String studentNumber);

    boolean existsByStudentNumber(String studentNumber);

    List<Student> findByStatus(StudentStatus status);

    List<Student> findByEducationLevel(EducationLevel educationLevel);

    List<Student> findByClassName(String className);

    Page<Student> findByStatus(StudentStatus status, Pageable pageable);

    Page<Student> findByEducationLevel(EducationLevel educationLevel, Pageable pageable);

    Page<Student> findByClassName(String className, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " +
           "(LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.studentNumber) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "s.status = :status")
    Page<Student> findBySearchAndStatus(@Param("search") String search, 
                                       @Param("status") StudentStatus status, 
                                       Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.enrollmentDate BETWEEN :startDate AND :endDate")
    List<Student> findByEnrollmentDateBetween(@Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.status = :status")
    long countByStatus(@Param("status") StudentStatus status);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.educationLevel = :level AND s.status = 'ACTIVE'")
    long countByEducationLevelAndStatusActive(@Param("level") EducationLevel level);

    @Query("SELECT s.className, COUNT(s) FROM Student s WHERE s.status = 'ACTIVE' GROUP BY s.className")
    List<Object[]> countActiveStudentsByClass();

    @Query("SELECT s FROM Student s WHERE " +
           "s.fatherPhone = :phone OR s.motherPhone = :phone OR s.guardianPhone = :phone")
    List<Student> findByParentPhone(@Param("phone") String phone);

    @Query("SELECT s FROM Student s WHERE s.birthDate BETWEEN :startDate AND :endDate")
    List<Student> findStudentsWithBirthdayBetween(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);
}

