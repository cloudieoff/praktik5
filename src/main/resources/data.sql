-- Факультеты
INSERT INTO faculties (name)
SELECT 'Факультет информационных технологий'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE name = 'Факультет информационных технологий');

INSERT INTO faculties (name)
SELECT 'Факультет экономики'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE name = 'Факультет экономики');

-- Группы
INSERT INTO groups (name, faculty_id)
SELECT 'ПИН-119', (SELECT id FROM faculties WHERE name = 'Факультет информационных технологий')
WHERE NOT EXISTS (SELECT 1 FROM groups WHERE name = 'ПИН-119');

INSERT INTO groups (name, faculty_id)
SELECT 'ПИН-120', (SELECT id FROM faculties WHERE name = 'Факультет информационных технологий')
WHERE NOT EXISTS (SELECT 1 FROM groups WHERE name = 'ПИН-120');

INSERT INTO groups (name, faculty_id)
SELECT 'ЭК-101', (SELECT id FROM faculties WHERE name = 'Факультет экономики')
WHERE NOT EXISTS (SELECT 1 FROM groups WHERE name = 'ЭК-101');

-- Студенты
INSERT INTO students (last_name, first_name, middle_name, gender, birth_date, age, nationality, height, weight, phone, address, group_id, faculty_id, course, average_score, specialty)
SELECT 'Иванов', 'Иван', 'Иванович', 'MALE', '2000-01-15', 26, 'Русский', 180.0, 75.0, '+79991234567', 'Москва, ул. Ленина, 1',
       (SELECT id FROM groups WHERE name = 'ПИН-119'),
       (SELECT id FROM faculties WHERE name = 'Факультет информационных технологий'),
       3, 4.5, 'Программист'
WHERE NOT EXISTS (SELECT 1 FROM students WHERE last_name = 'Иванов' AND first_name = 'Иван' AND middle_name = 'Иванович');

INSERT INTO students (last_name, first_name, middle_name, gender, birth_date, age, nationality, height, weight, phone, address, group_id, faculty_id, course, average_score, specialty)
SELECT 'Петрова', 'Мария', 'Сергеевна', 'FEMALE', '2001-07-20', 25, 'Русская', 165.0, 55.0, '+79997654321', 'Москва, ул. Пушкина, 2',
       (SELECT id FROM groups WHERE name = 'ПИН-120'),
       (SELECT id FROM faculties WHERE name = 'Факультет информационных технологий'),
       2, 4.8, 'Аналитик'
WHERE NOT EXISTS (SELECT 1 FROM students WHERE last_name = 'Петрова' AND first_name = 'Мария' AND middle_name = 'Сергеевна');

INSERT INTO students (last_name, first_name, middle_name, gender, birth_date, age, nationality, height, weight, phone, address, group_id, faculty_id, course, average_score, specialty)
SELECT 'Сидоров', 'Алексей', 'Петрович', 'MALE', '1999-12-10', 27, 'Русский', 175.0, 80.0, '+79991239876', 'Москва, ул. Гагарина, 3',
       (SELECT id FROM groups WHERE name = 'ЭК-101'),
       (SELECT id FROM faculties WHERE name = 'Факультет экономики'),
       4, 4.2, 'Экономист'
WHERE NOT EXISTS (SELECT 1 FROM students WHERE last_name = 'Сидоров' AND first_name = 'Алексей' AND middle_name = 'Петрович');