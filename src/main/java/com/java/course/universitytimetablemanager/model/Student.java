package com.java.course.universitytimetablemanager.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_groups",
            schema = "university_schedule",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_courses",
            schema = "university_schedule",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    public Student(Integer userId, String userName, String userLogin, String passwordHash, UserRole role) {
        super(userId, userName, userLogin, passwordHash, role, UserType.STUDENT);
    }

    public Student(List<Group> groups) {
        super(null, null, null, null, null, UserType.STUDENT);
        this.groups = groups;
    }

    public Student(Integer userId, String userName, String userLogin, String passwordHash, UserRole role, UserType userType, List<Group> groups) {
        super(userId, userName, userLogin, passwordHash, role, userType);
        this.groups = groups;
    }

    public Student(Integer userId, String userName, String userLogin, String passwordHash, UserRole role, List<Group> groups) {
        super(userId, userName, userLogin, passwordHash, role);
        this.groups = groups;
    }

    public Student() {

    }

    public Student(String firstName) {
    }


    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Student student = (Student) (o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation() : o);
        if (!super.equals(student)) return false;
        return Objects.equals(groups, student.groups);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                Objects.hash(super.hashCode(), groups);
    }


    @Override
    public String toString() {
        return "Student{" +
                "groups=" + groups +
                ", courses=" + courses +
                '}';
    }
}
