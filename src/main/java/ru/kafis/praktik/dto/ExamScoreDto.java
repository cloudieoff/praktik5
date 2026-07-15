package ru.kafis.praktik.dto;

import jakarta.validation.constraints.*;

public class ExamScoreDto {

    @NotBlank(message = "Название предмета обязательно")
    @Size(min = 2, max = 100, message = "Название предмета от 2 до 100 символов")
    private String subject;

    @NotNull(message = "Оценка обязательна")
    @Min(value = 0, message = "Оценка не может быть меньше 0")
    @Max(value = 100, message = "Оценка не может быть больше 100")
    private Integer score;

    public ExamScoreDto() {}

    public ExamScoreDto(String subject, Integer score) {
        this.subject = subject;
        this.score = score;
    }

    // Геттеры и сеттеры
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}