package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.model.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentService {

    @Transactional
    List<Student> findAllStudents();

    @Transactional
    Student findById(Integer studentId);


    @Transactional
    Student findStudentWithGroupsByUserId(Integer studentId);
}
