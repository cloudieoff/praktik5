-- 1. Вставка адресов (только если их нет)
INSERT INTO addresses (postal_code, country, region, district, city, street, house, apartment)
SELECT '101000', 'Россия', 'Московская область', 'г. Москва', 'Москва', 'ул. Тверская', '10', '5'
WHERE NOT EXISTS (
    SELECT 1 FROM addresses
    WHERE postal_code = '101000'
      AND country = 'Россия'
      AND city = 'Москва'
      AND street = 'ул. Тверская'
      AND house = '10'
);

INSERT INTO addresses (postal_code, country, region, district, city, street, house, apartment)
SELECT '102000', 'Россия', 'Санкт-Петербург', 'г. Санкт-Петербург', 'СПб', 'Невский пр.', '25', '12'
WHERE NOT EXISTS (
    SELECT 1 FROM addresses
    WHERE postal_code = '102000'
      AND country = 'Россия'
      AND city = 'СПб'
      AND street = 'Невский пр.'
      AND house = '25'
);

-- 2. Вставка абитуриентов (только если их нет)
-- Используем подзапросы для получения id адресов
INSERT INTO applicants (last_name, first_name, middle_name, gender, nationality, birth_date, address_id, passing_score)
SELECT 'Иванов', 'Иван', 'Иванович', 'MALE', 'Русский', '2000-01-15',
       (SELECT id FROM addresses WHERE postal_code = '101000' AND country = 'Россия' AND city = 'Москва' AND street = 'ул. Тверская' AND house = '10'),
       80.5
WHERE NOT EXISTS (
    SELECT 1 FROM applicants
    WHERE last_name = 'Иванов'
      AND first_name = 'Иван'
      AND middle_name = 'Иванович'
);

INSERT INTO applicants (last_name, first_name, middle_name, gender, nationality, birth_date, address_id, passing_score)
SELECT 'Петрова', 'Мария', 'Сергеевна', 'FEMALE', 'Русская', '2001-07-20',
       (SELECT id FROM addresses WHERE postal_code = '102000' AND country = 'Россия' AND city = 'СПб' AND street = 'Невский пр.' AND house = '25'),
       92.0
WHERE NOT EXISTS (
    SELECT 1 FROM applicants
    WHERE last_name = 'Петрова'
      AND first_name = 'Мария'
      AND middle_name = 'Сергеевна'
);

-- 3. Вставка оценок (проверяем по комбинации applicant_id + subject)
-- Используем подзапрос для получения id абитуриента
INSERT INTO exam_scores (subject, score, applicant_id)
SELECT 'Математика', 85, (SELECT id FROM applicants WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович')
WHERE NOT EXISTS (
    SELECT 1 FROM exam_scores
    WHERE subject = 'Математика'
      AND applicant_id = (SELECT id FROM applicants WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович')
);

INSERT INTO exam_scores (subject, score, applicant_id)
SELECT 'Русский язык', 78, (SELECT id FROM applicants WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович')
WHERE NOT EXISTS (
    SELECT 1 FROM exam_scores
    WHERE subject = 'Русский язык'
      AND applicant_id = (SELECT id FROM applicants WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович')
);

INSERT INTO exam_scores (subject, score, applicant_id)
SELECT 'Физика', 90, (SELECT id FROM applicants WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович')
WHERE NOT EXISTS (
    SELECT 1 FROM exam_scores
    WHERE subject = 'Физика'
      AND applicant_id = (SELECT id FROM applicants WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович')
);

INSERT INTO exam_scores (subject, score, applicant_id)
SELECT 'Математика', 95, (SELECT id FROM applicants WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна')
WHERE NOT EXISTS (
    SELECT 1 FROM exam_scores
    WHERE subject = 'Математика'
      AND applicant_id = (SELECT id FROM applicants WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна')
);

INSERT INTO exam_scores (subject, score, applicant_id)
SELECT 'Русский язык', 88, (SELECT id FROM applicants WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна')
WHERE NOT EXISTS (
    SELECT 1 FROM exam_scores
    WHERE subject = 'Русский язык'
      AND applicant_id = (SELECT id FROM applicants WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна')
);

INSERT INTO exam_scores (subject, score, applicant_id)
SELECT 'Информатика', 92, (SELECT id FROM applicants WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна')
WHERE NOT EXISTS (
    SELECT 1 FROM exam_scores
    WHERE subject = 'Информатика'
      AND applicant_id = (SELECT id FROM applicants WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна')
);