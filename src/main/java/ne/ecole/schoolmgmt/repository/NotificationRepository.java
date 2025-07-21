package ne.ecole.schoolmgmt.repository;

import ne.ecole.schoolmgmt.entity.Notification;
import ne.ecole.schoolmgmt.entity.NotificationStatus;
import ne.ecole.schoolmgmt.entity.NotificationType;
import ne.ecole.schoolmgmt.entity.NotificationChannel;
import ne.ecole.schoolmgmt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipient(User recipient);

    List<Notification> findByRecipientAndStatus(User recipient, NotificationStatus status);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByChannel(NotificationChannel channel);

    Page<Notification> findByRecipient(User recipient, Pageable pageable);

    Page<Notification> findByRecipientAndStatus(User recipient, NotificationStatus status, Pageable pageable);

    Page<Notification> findByStatus(NotificationStatus status, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.scheduledFor <= :now AND n.status = 'PENDING'")
    List<Notification> findScheduledNotifications(@Param("now") LocalDateTime now);

    @Query("SELECT n FROM Notification n WHERE n.status = 'FAILED' AND n.retryCount < :maxRetries")
    List<Notification> findFailedNotificationsForRetry(@Param("maxRetries") int maxRetries);

    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.readAt IS NULL")
    List<Notification> findUnreadByRecipient(@Param("recipient") User recipient);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.recipient = :recipient AND n.readAt IS NULL")
    long countUnreadByRecipient(@Param("recipient") User recipient);

    @Query("SELECT n FROM Notification n WHERE n.createdAt BETWEEN :startDate AND :endDate")
    List<Notification> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                             @Param("endDate") LocalDateTime endDate);

    @Query("SELECT n FROM Notification n WHERE n.sentAt BETWEEN :startDate AND :endDate")
    List<Notification> findBySentAtBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.status = :status")
    long countByStatus(@Param("status") NotificationStatus status);

    @Query("SELECT n.type, COUNT(n) FROM Notification n GROUP BY n.type")
    List<Object[]> getNotificationCountByType();

    @Query("SELECT n.channel, COUNT(n) FROM Notification n GROUP BY n.channel")
    List<Object[]> getNotificationCountByChannel();

    @Query("SELECT n.status, COUNT(n) FROM Notification n GROUP BY n.status")
    List<Object[]> getNotificationCountByStatus();

    @Query("SELECT n FROM Notification n WHERE n.recipientPhone = :phone AND n.channel = 'SMS'")
    List<Notification> findSMSNotificationsByPhone(@Param("phone") String phone);

    @Query("SELECT n FROM Notification n WHERE n.recipientEmail = :email AND n.channel = 'EMAIL'")
    List<Notification> findEmailNotificationsByEmail(@Param("email") String email);

    @Query("SELECT n FROM Notification n WHERE n.recipient IS NULL AND n.type = :type")
    List<Notification> findBroadcastNotificationsByType(@Param("type") NotificationType type);
}

