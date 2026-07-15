package ru.kafis.praktik.service;

import ru.kafis.praktik.dto.ApplicantCreateDto;
import ru.kafis.praktik.dto.ApplicantEditDto;
import ru.kafis.praktik.dto.ExamScoreDto;
import ru.kafis.praktik.entity.Address;
import ru.kafis.praktik.entity.Applicant;
import ru.kafis.praktik.entity.ExamScore;
import ru.kafis.praktik.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    public List<Applicant> findAll() {
        return applicantRepository.findAll();
    }

    public Optional<Applicant> findById(Long id) {
        return applicantRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return applicantRepository.existsById(id);
    }

    @Transactional
    public Applicant create(ApplicantCreateDto dto) {
        // Создаём адрес
        Address address = new Address(
                dto.getPostalCode(),
                dto.getCountry(),
                dto.getRegion(),
                dto.getDistrict(),
                dto.getCity(),
                dto.getStreet(),
                dto.getHouse(),
                dto.getApartment()
        );

        // Создаём абитуриента
        Applicant applicant = new Applicant();
        applicant.setLastName(dto.getLastName());
        applicant.setFirstName(dto.getFirstName());
        applicant.setMiddleName(dto.getMiddleName());
        applicant.setGender(dto.getGender());
        applicant.setNationality(dto.getNationality());
        applicant.setBirthDate(dto.getBirthDate());
        applicant.setAddress(address);
        applicant.setPassingScore(dto.getPassingScore());

        // Сохраняем абитуриента (каскадно сохранится адрес)
        Applicant saved = applicantRepository.save(applicant);

        // Добавляем оценки
        if (dto.getExamScores() != null) {
            List<ExamScore> scores = dto.getExamScores().stream()
                    .map(esDto -> {
                        ExamScore es = new ExamScore(esDto.getSubject(), esDto.getScore());
                        es.setApplicant(saved);
                        return es;
                    })
                    .collect(Collectors.toList());
            saved.setExamScores(scores);
        }

        return applicantRepository.save(saved);
    }

    @Transactional
    public Applicant update(ApplicantEditDto dto) {
        Applicant applicant = applicantRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Абитуриент не найден"));

        // Обновляем основные поля
        applicant.setLastName(dto.getLastName());
        applicant.setFirstName(dto.getFirstName());
        applicant.setMiddleName(dto.getMiddleName());
        applicant.setGender(dto.getGender());
        applicant.setNationality(dto.getNationality());
        applicant.setBirthDate(dto.getBirthDate());
        applicant.setPassingScore(dto.getPassingScore());

        // Обновляем адрес (либо заменяем)
        Address address = applicant.getAddress();
        if (address == null) {
            address = new Address();
            applicant.setAddress(address);
        }
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setRegion(dto.getRegion());
        address.setDistrict(dto.getDistrict());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouse(dto.getHouse());
        address.setApartment(dto.getApartment());

        // Обновляем оценки: удаляем старые и добавляем новые
        applicant.getExamScores().clear();
        if (dto.getExamScores() != null) {
            List<ExamScore> newScores = dto.getExamScores().stream()
                    .map(esDto -> {
                        ExamScore es = new ExamScore(esDto.getSubject(), esDto.getScore());
                        es.setApplicant(applicant);
                        return es;
                    })
                    .collect(Collectors.toList());
            applicant.getExamScores().addAll(newScores);
        }

        return applicantRepository.save(applicant);
    }

    @Transactional
    public void delete(Long id) {
        applicantRepository.deleteById(id);
    }

    public List<Applicant> search(String lastName, Double minPassingScore, String subject) {
        return applicantRepository.search(lastName, minPassingScore, subject);
    }
}