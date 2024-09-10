package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Optional<Tutor> findTutorWithCoursesByUserId(Integer userId);
}
