package com.java.course.universitytimetablemanager.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;


import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "groups", schema = "university_schedule")
public class Group {
    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "group_sequence"

    )
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "group_name")
    private String groupName;


    @ManyToMany
    @JoinTable(name = "groups_courses",
            schema = "university_schedule",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;

    public Group(Integer groupId, String groupName, List<Course> courses) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.courses = courses;
    }

    public Group(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }


    public Group() {

    }

    public Group(String groupName) {
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Group group = (Group) o;
        return groupId != null && Objects.equals(groupId, group.groupId);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            if (!student.getGroups().contains(this)) {
                student.getGroups().add(this);
            }
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getGroups().remove(this);
    }
}
