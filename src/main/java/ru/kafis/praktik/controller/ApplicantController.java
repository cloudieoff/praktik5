package ru.kafis.praktik.controller;

import ru.kafis.praktik.dto.ApplicantCreateDto;
import ru.kafis.praktik.dto.ApplicantEditDto;
import ru.kafis.praktik.entity.Applicant;
import ru.kafis.praktik.service.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/applicants";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("applicants", applicantService.findAll());
        return "applicants/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("applicantCreateDto", new ApplicantCreateDto());
        return "applicants/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("applicantCreateDto") ApplicantCreateDto dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "applicants/create";
        }
        applicantService.create(dto);
        return "redirect:/applicants";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Applicant applicant = applicantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Абитуриент не найден"));

        ApplicantEditDto dto = new ApplicantEditDto();
        dto.setId(applicant.getId());
        dto.setLastName(applicant.getLastName());
        dto.setFirstName(applicant.getFirstName());
        dto.setMiddleName(applicant.getMiddleName());
        dto.setGender(applicant.getGender());
        dto.setNationality(applicant.getNationality());
        dto.setBirthDate(applicant.getBirthDate());
        dto.setPassingScore(applicant.getPassingScore());

        if (applicant.getAddress() != null) {
            dto.setPostalCode(applicant.getAddress().getPostalCode());
            dto.setCountry(applicant.getAddress().getCountry());
            dto.setRegion(applicant.getAddress().getRegion());
            dto.setDistrict(applicant.getAddress().getDistrict());
            dto.setCity(applicant.getAddress().getCity());
            dto.setStreet(applicant.getAddress().getStreet());
            dto.setHouse(applicant.getAddress().getHouse());
            dto.setApartment(applicant.getAddress().getApartment());
        }

        // Оценки
        if (applicant.getExamScores() != null) {
            dto.setExamScores(
                    applicant.getExamScores().stream()
                            .map(es -> new ru.kafis.praktik.dto.ExamScoreDto(es.getSubject(), es.getScore()))
                            .collect(java.util.stream.Collectors.toList())
            );
        }

        model.addAttribute("applicantEditDto", dto);
        return "applicants/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("applicantEditDto") ApplicantEditDto dto,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            return "applicants/edit";
        }
        applicantService.update(dto);
        return "redirect:/applicants";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (applicantService.existsById(id)) {
            applicantService.delete(id);
        }
        return "redirect:/applicants";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Applicant applicant = applicantService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Абитуриент не найден"));
        model.addAttribute("applicant", applicant);
        return "applicants/details";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false) String lastName,
                         @RequestParam(required = false) Double minPassingScore,
                         @RequestParam(required = false) String subject,
                         Model model) {
        System.out.println(">>> Поиск: lastName=" + lastName +
                ", minPassingScore=" + minPassingScore +
                ", subject=" + subject);

        List<Applicant> applicants = applicantService.search(lastName, minPassingScore, subject);
        System.out.println(">>> Найдено записей: " + applicants.size());

        model.addAttribute("applicants", applicants);
        model.addAttribute("lastName", lastName);
        model.addAttribute("minPassingScore", minPassingScore);
        model.addAttribute("subject", subject);
        return "applicants/list";
    }
}