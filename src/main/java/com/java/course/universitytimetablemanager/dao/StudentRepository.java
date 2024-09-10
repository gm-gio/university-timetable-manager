package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUserName(String userName);

    Student findByUserId(Integer userId);

    Optional<Student> findStudentWithGroupsByUserId(Integer userId);
}
