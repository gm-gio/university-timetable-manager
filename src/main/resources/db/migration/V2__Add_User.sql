-- adminLogin/123
INSERT INTO university_schedule.users (user_id, user_type, user_name, user_login, password_hash, role)
VALUES (100002, 'USER', 'Admin', 'adminLogin', '$2a$10$5abLA.LtjfC2XF0mfkyPJuimjyivb0TpICDCRS5fXZoy3elvgw2eC', 'ADMIN');
-- userLogin/111
INSERT INTO university_schedule.users (user_id, user_type, user_name, user_login, password_hash, role)
VALUES (100003, 'USER', 'User', 'userLogin', '$2a$10$ayhDhblaT1Rs3KQDCeGRAOOhEoRE67Mf4hYAEJM2eSM/Bm/G9Eex6', 'USER');
-- tutorLogin/111
INSERT INTO university_schedule.users (user_id, user_type, user_name, user_login, password_hash, role)
VALUES (100004, 'TUTOR', 'Tutor', 'tutorLogin', '$2a$10$ayhDhblaT1Rs3KQDCeGRAOOhEoRE67Mf4hYAEJM2eSM/Bm/G9Eex6',
        'TUTOR');
-- studentLogin/111
INSERT INTO university_schedule.users (user_id, user_type, user_name, user_login, password_hash, role)
VALUES (100005, 'STUDENT', 'Student', 'studentLogin', '$2a$10$ayhDhblaT1Rs3KQDCeGRAOOhEoRE67Mf4hYAEJM2eSM/Bm/G9Eex6',
        'STUDENT');
--staffLogin/111
INSERT INTO university_schedule.users (user_id, user_type, user_name, user_login, password_hash, role)
VALUES (100006, 'USER', 'Staff', 'staffLogin', '$2a$10$ayhDhblaT1Rs3KQDCeGRAOOhEoRE67Mf4hYAEJM2eSM/Bm/G9Eex6', 'STAFF');


INSERT INTO university_schedule.audiences (location_id, audiences_name)
VALUES (100001, 'Lecture Hall 1'),
       (100002, 'Lecture Hall 2'),
       (100003, 'Lab Room A'),
       (100004, 'Lab Room B');

INSERT INTO university_schedule.courses (course_id, course_name, course_description)
VALUES (100001, 'Mathematics', 'Introduction to Mathematics'),
       (100002, 'Physics', 'Basics of Physics'),
       (100003, 'Chemistry', 'Chemistry for Beginners'),
       (100004, 'Biology', 'Fundamentals of Biology');

INSERT INTO university_schedule.groups (group_id, group_name)
VALUES (100001, 'Group A'),
       (100002, 'Group B'),
       (100003, 'Group C'),
       (100004, 'Group D');


INSERT INTO university_schedule.schedules (schedule_id, schedule_date, lesson_num)
VALUES (100001, '2024-09-01',1),
       (100002, '2024-09-01',2),
       (100003, '2024-09-02',3),
       (100004, '2024-09-02',4);