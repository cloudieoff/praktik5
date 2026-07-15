package ru.kafis.praktik.repository;

import ru.kafis.praktik.entity.ExamScore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamScoreRepository extends JpaRepository<ExamScore, Long> {
    List<ExamScore> findByApplicantId(Long applicantId);
}