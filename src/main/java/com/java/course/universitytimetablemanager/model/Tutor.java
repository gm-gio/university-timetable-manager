package com.java.course.universitytimetablemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("TUTOR")
public class Tutor extends User {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tutors_courses",
            schema = "university_schedule",
            joinColumns = @JoinColumn(name = "tutor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    private List<Course> courses;



    public Tutor(Integer userId, String userName, String userLogin, String passwordHash, UserRole role) {
        super(userId, userName, userLogin, passwordHash, role, UserType.TUTOR);
    }

    public Tutor(List<Course> courses) {
        super(null, null, null, null, null, UserType.TUTOR);
        this.courses = courses;
    }

    public Tutor(Integer userId, String userName, String userLogin, String passwordHash, UserRole role, UserType userType, List<Course> courses) {
        super(userId, userName, userLogin, passwordHash, role, userType);
        this.courses = courses;
    }


    public Tutor() {
    }

    public Tutor(String tutorName) {
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
        Tutor tutor = (Tutor) (o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation() : o);
        return Objects.equals(courses, tutor.courses);
    }


    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Tutor{" +
                "userId=" + getUserId() +
                ", userName='" + getUserName() + '\'' +
                ", userLogin='" + getUserLogin() + '\'' +
                '}';
    }
}
