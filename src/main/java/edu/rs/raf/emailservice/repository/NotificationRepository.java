package edu.rs.raf.emailservice.repository;

import edu.rs.raf.emailservice.domain.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<EmailNotification, Long> {
    List<EmailNotification> findAll();
    Optional<EmailNotification> findEmailNotificationByType(String type);
}
