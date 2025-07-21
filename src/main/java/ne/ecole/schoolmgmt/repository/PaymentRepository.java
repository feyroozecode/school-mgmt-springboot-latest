package ne.ecole.schoolmgmt.repository;

import ne.ecole.schoolmgmt.entity.Payment;
import ne.ecole.schoolmgmt.entity.PaymentStatus;
import ne.ecole.schoolmgmt.entity.PaymentType;
import ne.ecole.schoolmgmt.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByReceiptNumber(String receiptNumber);

    List<Payment> findByStudent(Student student);

    List<Payment> findByStudentAndAcademicYear(Student student, String academicYear);

    List<Payment> findByStudentAndPaymentType(Student student, PaymentType paymentType);

    List<Payment> findByStatus(PaymentStatus status);

    Page<Payment> findByStudent(Student student, Pageable pageable);

    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);

    Page<Payment> findByPaymentType(PaymentType paymentType, Pageable pageable);

    Page<Payment> findByAcademicYear(String academicYear, Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findByPaymentDateBetween(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Page<Payment> findByPaymentDateBetween(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate, 
                                          Pageable pageable);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'")
    BigDecimal getTotalCompletedPayments();

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED' AND p.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalPaymentsBetweenDates(@Param("startDate") LocalDate startDate, 
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.student = :student AND p.status = 'COMPLETED'")
    BigDecimal getTotalPaymentsByStudent(@Param("student") Student student);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.student = :student AND p.academicYear = :academicYear AND p.status = 'COMPLETED'")
    BigDecimal getTotalPaymentsByStudentAndYear(@Param("student") Student student, 
                                               @Param("academicYear") String academicYear);

    @Query("SELECT p.paymentType, SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED' GROUP BY p.paymentType")
    List<Object[]> getPaymentSummaryByType();

    @Query("SELECT p.paymentType, SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED' AND p.academicYear = :academicYear GROUP BY p.paymentType")
    List<Object[]> getPaymentSummaryByTypeAndYear(@Param("academicYear") String academicYear);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    long countByStatus(@Param("status") PaymentStatus status);

    @Query("SELECT DISTINCT p.academicYear FROM Payment p ORDER BY p.academicYear DESC")
    List<String> findDistinctAcademicYears();

    @Query("SELECT p FROM Payment p WHERE p.student.id = :studentId AND p.paymentType = :paymentType AND p.academicYear = :academicYear")
    List<Payment> findByStudentIdAndPaymentTypeAndAcademicYear(@Param("studentId") Long studentId,
                                                              @Param("paymentType") PaymentType paymentType,
                                                              @Param("academicYear") String academicYear);
}

