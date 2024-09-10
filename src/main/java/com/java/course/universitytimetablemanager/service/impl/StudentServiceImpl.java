package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.StudentRepository;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public List<Student> findAllStudents(){
       return studentRepository.findAll();
    }

    @Override
    @Transactional
    public Student findById(Integer studentId){
        return studentRepository.findByUserId(studentId);
    }

    @Override
    @Transactional
    public Student findStudentWithGroupsByUserId(Integer studentId){
        return studentRepository.findStudentWithGroupsByUserId(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found"));
    }


}
