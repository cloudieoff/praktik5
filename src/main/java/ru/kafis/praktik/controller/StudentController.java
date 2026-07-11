package ru.kafis.praktik.controller;

import ru.kafis.praktik.dto.StudentCreateDto;
import ru.kafis.praktik.dto.StudentEditDto;
import ru.kafis.praktik.entity.Student;
import ru.kafis.praktik.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Перенаправление с корня на список студентов
    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/students";
    }

    // Главная страница со списком всех студентов
    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.findAll());
        // для формы поиска
        model.addAttribute("groups", studentService.findAllGroups());
        model.addAttribute("faculties", studentService.findAllFaculties());
        return "students/list";
    }

    // Форма создания
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("studentCreateDto", new StudentCreateDto());
        model.addAttribute("groups", studentService.findAllGroups());
        model.addAttribute("faculties", studentService.findAllFaculties());
        return "students/create";
    }

    // Обработка создания
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("studentCreateDto") StudentCreateDto dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", studentService.findAllGroups());
            model.addAttribute("faculties", studentService.findAllFaculties());
            return "students/create";
        }
        studentService.create(dto);
        return "redirect:/students";
    }

    // Форма редактирования
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));

        StudentEditDto dto = new StudentEditDto();
        dto.setId(student.getId());
        dto.setLastName(student.getLastName());
        dto.setFirstName(student.getFirstName());
        dto.setMiddleName(student.getMiddleName());
        dto.setGender(student.getGender());
        dto.setBirthDate(student.getBirthDate());
        dto.setAge(student.getAge());
        dto.setNationality(student.getNationality());
        dto.setHeight(student.getHeight());
        dto.setWeight(student.getWeight());
        dto.setPhone(student.getPhone());
        dto.setAddress(student.getAddress());
        dto.setGroupId(student.getGroup() != null ? student.getGroup().getId() : null);
        dto.setFacultyId(student.getFaculty() != null ? student.getFaculty().getId() : null);
        dto.setCourse(student.getCourse());
        dto.setAverageScore(student.getAverageScore());
        dto.setSpecialty(student.getSpecialty());

        model.addAttribute("studentEditDto", dto);
        model.addAttribute("groups", studentService.findAllGroups());
        model.addAttribute("faculties", studentService.findAllFaculties());
        return "students/edit";
    }

    // Обработка редактирования
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("studentEditDto") StudentEditDto dto,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", studentService.findAllGroups());
            model.addAttribute("faculties", studentService.findAllFaculties());
            return "students/edit";
        }
        studentService.update(dto);
        return "redirect:/students";
    }

    // Удаление
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (studentService.existsById(id)) {
            studentService.delete(id);
        }
        return "redirect:/students";
    }

    // Детальная информация
    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));
        model.addAttribute("student", student);
        return "students/details";
    }

    // Поиск (POST)
    @PostMapping("/search")
    public String search(@RequestParam(required = false) String lastName,
                         @RequestParam(required = false) Long groupId,
                         @RequestParam(required = false) Long facultyId,
                         Model model) {
        List<Student> students = studentService.search(lastName, groupId, facultyId);
        model.addAttribute("students", students);
        model.addAttribute("groups", studentService.findAllGroups());
        model.addAttribute("faculties", studentService.findAllFaculties());
        model.addAttribute("lastName", lastName);
        model.addAttribute("groupId", groupId);
        model.addAttribute("facultyId", facultyId);
        return "students/list";
    }
}