package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.dto.CourseDTO;
import com.java.course.universitytimetablemanager.model.Course;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {


    Course save(CourseDTO courseDTO);

    void deleteCourse(Course course);

    List<Course> getAllCourses();

    @Transactional
    void deleteById(Integer id);


    Course findById(Integer id);


    @Transactional
    void updateCourse(CourseDTO updatedCourse);

    void addTutorToCourse(Integer tutorId, Integer courseId);

    void removeTutorFromCourse(Integer tutorId, Integer courseId);

    void addGroupToCourse(Integer groupId, Integer courseId);

    void removeGroupFromCourse(Integer groupId, Integer courseId);

    @Transactional
    void removeStudentFromCourse(Integer studentId, Integer courseId);

    @Transactional
    void addStudentToCourse(Integer studentId, Integer courseId);
}
