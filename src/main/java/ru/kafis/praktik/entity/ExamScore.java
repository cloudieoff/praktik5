package ru.kafis.praktik.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "exam_scores")
public class ExamScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название предмета обязательно")
    @Size(min = 2, max = 100, message = "Название предмета от 2 до 100 символов")
    private String subject;

    @NotNull(message = "Оценка обязательна")
    @Min(value = 0, message = "Оценка не может быть меньше 0")
    @Max(value = 100, message = "Оценка не может быть больше 100")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    public ExamScore() {}

    public ExamScore(String subject, Integer score) {
        this.subject = subject;
        this.score = score;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Applicant getApplicant() { return applicant; }
    public void setApplicant(Applicant applicant) { this.applicant = applicant; }
}