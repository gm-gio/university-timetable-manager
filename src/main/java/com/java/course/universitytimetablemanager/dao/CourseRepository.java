package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.Course;
import com.java.course.universitytimetablemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findCourseByCourseName(String username);
}
