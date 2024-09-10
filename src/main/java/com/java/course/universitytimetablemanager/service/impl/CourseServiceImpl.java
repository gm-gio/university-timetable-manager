package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.CourseRepository;
import com.java.course.universitytimetablemanager.dao.GroupRepository;
import com.java.course.universitytimetablemanager.dao.StudentRepository;
import com.java.course.universitytimetablemanager.dao.TutorRepository;
import com.java.course.universitytimetablemanager.dto.CourseDTO;
import com.java.course.universitytimetablemanager.model.Course;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TutorRepository tutorRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;


    public CourseServiceImpl(CourseRepository courseRepository, TutorRepository tutorRepository, GroupRepository groupRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.tutorRepository = tutorRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    private boolean courseNameExists(String courseName) {
        return courseRepository.findCourseByCourseName(courseName).isPresent();
    }

    @Override
    @Transactional
    public Course save(CourseDTO courseDTO) {
        if (courseNameExists(courseDTO.getCourseName())) {
            throw new RuntimeException("Course with name " + courseDTO.getCourseName() + " already exists.");
        }

        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setCourseDescription(courseDTO.getCourseDescription());

        return courseRepository.save(course);
    }


    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }


    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course findById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateCourse(CourseDTO updatedCourse) {
        Course existingCourse = courseRepository.findById(updatedCourse.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + updatedCourse.getCourseId()));
        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setCourseDescription(updatedCourse.getCourseDescription());
        courseRepository.save(existingCourse);
    }


    @Override
    @Transactional
    public void addTutorToCourse(Integer tutorId, Integer courseId) {

        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(() -> new EntityNotFoundException("Tutor not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not Found"));


        if (tutor != null && course != null) {
            course.addTutor(tutor);
            courseRepository.save(course);
        }
    }

    @Override
    @Transactional
    public void removeTutorFromCourse(Integer tutorId, Integer courseId) {

        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(() -> new EntityNotFoundException("Tutor not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not Found"));

        if (tutor != null && course != null) {
            course.removeTutor(tutor);
        }
    }


    @Override
    @Transactional
    public void addGroupToCourse(Integer groupId, Integer courseId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (group != null && course != null) {
            course.addGroup(group);
            courseRepository.save(course);
        }
    }

    @Override
    @Transactional
    public void removeGroupFromCourse(Integer groupId, Integer courseId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (group != null && course != null) {
            course.removeGroup(group);
        }
    }

    @Override
    @Transactional
    public void removeStudentFromCourse(Integer studentId, Integer courseId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course not found"));

        if (student != null && course != null){
            course.removeStudent(student);
        }
    }

    @Override
    @Transactional
    public void addStudentToCourse(Integer studentId, Integer courseId){

        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course not found"));

        if (student != null && course != null){
            course.addStudent(student);
            courseRepository.save(course);
        }
    }
}
