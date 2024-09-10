package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.CourseRepository;
import com.java.course.universitytimetablemanager.dao.GroupRepository;
import com.java.course.universitytimetablemanager.dao.StudentRepository;
import com.java.course.universitytimetablemanager.dao.TutorRepository;
import com.java.course.universitytimetablemanager.model.Course;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.service.UserGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserGeneratorServiceImpl implements UserGeneratorService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;


    @Autowired

    public UserGeneratorServiceImpl(StudentRepository studentRepository,
                                    GroupRepository groupRepository,
                                    TutorRepository tutorRepository,
                                    CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.tutorRepository = tutorRepository;
        this.courseRepository = courseRepository;
    }

    private final String[] firstNames = {"John", "Jane", "Michael", "Emily", "David", "Emma", "Christopher", "Olivia", "Andrew",
            "Sophia", "Daniel", "Mia", "Ethan", "Isabella", "Matthew", "Ava", "Alexander", "Grace",
            "James", "Lily"};

    @Override
    public void generateStudentIfNeed() {
        if (studentRepository.count() == 0) {
            List<Student> students = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                String firstName = firstNames[(int) (Math.random() * firstNames.length)];

                Student student = new Student(firstName);
                students.add(student);
            }
            studentRepository.saveAll(students);
        }
    }

    @Override
    public void assignStudentsToGroups() {
        List<Student> students = studentRepository.findAll();
        List<Group> groups = groupRepository.findAll();
        for (Student student : students) {
            Group group = groups.get((int) (Math.random() * groups.size()));
            student.getGroups().add(group);
        }
        studentRepository.saveAll(students);
    }


    private final String[] tutorNames = {"Peter", "Alice", "Kevin", "Linda", "Robert",
            "Mary", "Jack", "Sophia", "William", "Emma"};

    @Override
    public void generateTutorsIfNeed() {
        if (tutorRepository.count() == 0) {
            List<Tutor> tutors = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                String tutorName = tutorNames[(int) (Math.random() * tutorNames.length)];

                Tutor tutor = new Tutor(tutorName);
                tutors.add(tutor);
            }
            tutorRepository.saveAll(tutors);
        }

    }

    @Override
    public void assignTutorsToCourse() {
        List<Tutor> tutors = tutorRepository.findAll();
        List<Course> courses = courseRepository.findAll();
        for (Tutor tutor : tutors) {
            Course course = courses.get((int) (Math.random() * courses.size()));
            tutor.setCourses((List<Course>) course);
            tutorRepository.save(tutor);

        }
    }
}
