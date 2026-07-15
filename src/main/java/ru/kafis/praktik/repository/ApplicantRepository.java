package ru.kafis.praktik.repository;

import ru.kafis.praktik.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    @Query("SELECT DISTINCT a FROM Applicant a " +
            "LEFT JOIN a.examScores e " +
            "WHERE (COALESCE(:lastName, '') = '' OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) " +
            "AND (:minPassingScore IS NULL OR a.passingScore >= :minPassingScore) " +
            "AND (COALESCE(:subject, '') = '' OR LOWER(e.subject) = LOWER(:subject))")
    List<Applicant> search(@Param("lastName") String lastName,
                           @Param("minPassingScore") Double minPassingScore,
                           @Param("subject") String subject);
}