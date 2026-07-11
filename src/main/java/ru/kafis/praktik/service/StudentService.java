package ru.kafis.praktik.service;

import ru.kafis.praktik.dto.StudentCreateDto;
import ru.kafis.praktik.dto.StudentEditDto;
import ru.kafis.praktik.entity.Student;
import ru.kafis.praktik.entity.Group;
import ru.kafis.praktik.entity.Faculty;
import ru.kafis.praktik.repository.StudentRepository;
import ru.kafis.praktik.repository.GroupRepository;
import ru.kafis.praktik.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    @Transactional
    public Student create(StudentCreateDto dto) {
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Группа не найдена"));
        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new IllegalArgumentException("Факультет не найден"));

        Student student = new Student();
        student.setLastName(dto.getLastName());
        student.setFirstName(dto.getFirstName());
        student.setMiddleName(dto.getMiddleName());
        student.setGender(dto.getGender());
        student.setBirthDate(dto.getBirthDate());
        student.setAge(dto.getAge());
        student.setNationality(dto.getNationality());
        student.setHeight(dto.getHeight());
        student.setWeight(dto.getWeight());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setGroup(group);
        student.setFaculty(faculty);
        student.setCourse(dto.getCourse());
        student.setAverageScore(dto.getAverageScore());
        student.setSpecialty(dto.getSpecialty());

        return studentRepository.save(student);
    }

    @Transactional
    public Student update(StudentEditDto dto) {
        Student student = studentRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));

        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Группа не найдена"));
        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new IllegalArgumentException("Факультет не найден"));

        student.setLastName(dto.getLastName());
        student.setFirstName(dto.getFirstName());
        student.setMiddleName(dto.getMiddleName());
        student.setGender(dto.getGender());
        student.setBirthDate(dto.getBirthDate());
        student.setAge(dto.getAge());
        student.setNationality(dto.getNationality());
        student.setHeight(dto.getHeight());
        student.setWeight(dto.getWeight());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setGroup(group);
        student.setFaculty(faculty);
        student.setCourse(dto.getCourse());
        student.setAverageScore(dto.getAverageScore());
        student.setSpecialty(dto.getSpecialty());

        return studentRepository.save(student);
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> search(String lastName, Long groupId, Long facultyId) {
        return studentRepository.search(lastName, groupId, facultyId);
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    public List<Faculty> findAllFaculties() {
        return facultyRepository.findAll();
    }
}