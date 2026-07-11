package ru.kafis.praktik.repository;

import ru.kafis.praktik.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Комбинированный поиск по фамилии (часть), группе и факультету (опционально)
    @Query("SELECT s FROM Student s WHERE " +
            "(:lastName IS NULL OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:groupId IS NULL OR s.group.id = :groupId) AND " +
            "(:facultyId IS NULL OR s.faculty.id = :facultyId)")
    List<Student> search(@Param("lastName") String lastName,
                         @Param("groupId") Long groupId,
                         @Param("facultyId") Long facultyId);
}