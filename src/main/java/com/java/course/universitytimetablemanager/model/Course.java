package com.java.course.universitytimetablemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;


import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "courses", schema = "university_schedule")
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "course_sequence"

    )
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_description")
    private String courseDescription;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Tutor> tutors;

    @ManyToMany(mappedBy = "courses",  fetch = FetchType.EAGER)
    private List<Group> groups;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private List<Student> students;

    public Course(Integer courseId, String courseName, String courseDescription, List<Tutor> tutors, List<Group> groups) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.tutors = tutors;
        this.groups = groups;
    }

    public Course(Integer courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Course() {

    }

    public Course(String courseName, String courseDescription) {
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public List<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutor> tutors) {
        this.tutors = tutors;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Course course = (Course) (o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation() : o);
        return Objects.equals(courseId, course.courseId) &&
                Objects.equals(courseName, course.courseName) &&
                Objects.equals(courseDescription, course.courseDescription) &&
                Objects.equals(tutors, course.tutors);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }
    public void removeTutor(Tutor tutor) {
        tutors.remove(tutor);
        tutor.getCourses().remove(this);
    }

    public void addTutor(Tutor tutor) {
        if (!tutors.contains(tutor)) {
            tutors.add(tutor);
            if (!tutor.getCourses().contains(this)) {
                tutor.getCourses().add(this);
            }
        }
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.getCourses().remove(this);
    }


    public void addGroup(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
            if (!group.getCourses().contains(this)) {
                group.getCourses().add(this);
            }
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            if (!student.getCourses().contains(this)) {
                student.getCourses().add(this);
            }
        }
    }
}
