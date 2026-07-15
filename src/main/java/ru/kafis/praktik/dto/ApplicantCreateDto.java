package ru.kafis.praktik.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import ru.kafis.praktik.entity.Applicant.Gender;
import java.time.LocalDate;
import java.util.List;

public class ApplicantCreateDto {

    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 50, message = "Фамилия от 2 до 50 символов")
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Имя от 2 до 50 символов")
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotNull(message = "Пол обязателен")
    private Gender gender;

    private String nationality;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;

    // Поля адреса
    private String postalCode;
    private String country;
    private String region;
    private String district;
    private String city;
    private String street;
    private String house;
    private String apartment;

    // Список оценок
    @Valid
    private List<ExamScoreDto> examScores;

    private Double passingScore;

    // Геттеры и сеттеры
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouse() { return house; }
    public void setHouse(String house) { this.house = house; }

    public String getApartment() { return apartment; }
    public void setApartment(String apartment) { this.apartment = apartment; }

    public List<ExamScoreDto> getExamScores() { return examScores; }
    public void setExamScores(List<ExamScoreDto> examScores) { this.examScores = examScores; }

    public Double getPassingScore() { return passingScore; }
    public void setPassingScore(Double passingScore) { this.passingScore = passingScore; }
}