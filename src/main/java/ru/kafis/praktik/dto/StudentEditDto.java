package ru.kafis.praktik.dto;

import jakarta.validation.constraints.NotNull;

public class StudentEditDto extends StudentCreateDto {

    @NotNull(message = "ID обязателен")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}