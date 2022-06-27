package edu.rs.raf.emailservice.repository;

import edu.rs.raf.emailservice.domain.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
    List<SentEmail> findAllByEmail(String email);
    List<SentEmail> findAllByType(String type);
    List<SentEmail> findAllByDateSentAfter(Date date);
}
