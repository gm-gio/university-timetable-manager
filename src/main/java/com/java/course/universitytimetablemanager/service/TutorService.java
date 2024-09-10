package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.model.Tutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TutorService {
    @Transactional
    List<Tutor> getAllTutors();

    @Transactional
    Tutor getTutorsWithCourses(Integer tutorId);

    Optional<Tutor> findById(Integer tutorId);
}
