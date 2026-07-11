package ru.kafis.praktik.repository;

import ru.kafis.praktik.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}