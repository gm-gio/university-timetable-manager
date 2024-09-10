CREATE SCHEMA IF NOT EXISTS university_schedule;

CREATE SEQUENCE university_schedule.audiences_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

CREATE SEQUENCE university_schedule.course_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

CREATE SEQUENCE university_schedule.group_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

CREATE SEQUENCE university_schedule.schedule_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

CREATE SEQUENCE university_schedule.user_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

CREATE TABLE IF NOT EXISTS university_schedule.courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(255) NOT NULL,
    course_description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS university_schedule.groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS university_schedule.audiences
(
    location_id    SERIAL PRIMARY KEY,
    audiences_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS university_schedule.users
(
    user_id       SERIAL PRIMARY KEY,
    user_type     VARCHAR(10),
    user_name     VARCHAR(255) NOT NULL,
    user_login    VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    group_id      INT REFERENCES university_schedule.groups (group_id) ON DELETE CASCADE,
    course_id     INT REFERENCES university_schedule.courses (course_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS university_schedule.tutors_courses
(
    tutor_id  INT,
    course_id INT,
    PRIMARY KEY (tutor_id, course_id),
    FOREIGN KEY (tutor_id) REFERENCES university_schedule.users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES university_schedule.courses (course_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS university_schedule.groups_courses
(
    group_id  INT,
    course_id INT,
    PRIMARY KEY (course_id, group_id),
    FOREIGN KEY (course_id) REFERENCES university_schedule.courses (course_id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES university_schedule.groups (group_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS university_schedule.students_courses
(
    student_id  INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES university_schedule.users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES university_schedule.courses (course_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS university_schedule.schedules
(
    schedule_id SERIAL PRIMARY KEY,
    schedule_date DATE NOT NULL,
    lesson_num INT NOT NULL,
    tutor_id INT,
    location_id INT,
    course_id INT,
    group_id INT,
    FOREIGN KEY (tutor_id) REFERENCES university_schedule.users (user_id) ON DELETE SET NULL,
    FOREIGN KEY (location_id) REFERENCES university_schedule.audiences (location_id) ON DELETE SET NULL,
    FOREIGN KEY (course_id) REFERENCES university_schedule.courses (course_id) ON DELETE SET NULL,
    FOREIGN KEY (group_id) REFERENCES university_schedule.groups (group_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS university_schedule.students
(
    student_id       SERIAL PRIMARY KEY,
    student_name     VARCHAR(255) NOT NULL,
    student_password VARCHAR(255) NOT NULL,
    group_id         INT          NOT NULL,
    FOREIGN KEY (group_id) REFERENCES university_schedule.groups (group_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS university_schedule.students_groups
(
    student_id  INT,
    group_id INT,
    PRIMARY KEY (student_id, group_id),
    FOREIGN KEY (student_id) REFERENCES university_schedule.users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES university_schedule.groups (group_id) ON DELETE CASCADE
);
