package ru.kafis.praktik.repository;

import ru.kafis.praktik.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}