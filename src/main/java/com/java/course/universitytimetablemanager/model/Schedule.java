package com.java.course.universitytimetablemanager.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "schedules", schema = "university_schedule")
public class Schedule {
    @Id
    @SequenceGenerator(
            name = "schedule_sequence",
            sequenceName = "schedule_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "schedule_sequence"

    )
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @Column(name = "schedule_date")
    private LocalDate scheduleDate;
    @Column(name = "lesson_num")
    private Integer lessonNum;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Audience audience;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;


    public Schedule(Integer scheduleId, LocalDate scheduleDate, Integer lessonNum,
                    Tutor tutor, Audience audience, Course course, Group group) {
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.lessonNum = lessonNum;
        this.tutor = tutor;
        this.audience = audience;
        this.course = course;
        this.group = group;
    }

    public Schedule() {

    }

    public Schedule(LocalDate now, int lessonNum, Tutor tutor, Group group, Audience audience, Course course) {
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
        Schedule schedule = (Schedule) (o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation() : o);
        return Objects.equals(scheduleId, schedule.scheduleId);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                Objects.hash(scheduleId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", scheduleDate=" + scheduleDate +
                ", lessonNum=" + lessonNum +
                ", tutor=" + tutor +
                ", audience=" + audience +
                ", course=" + course +
                ", group=" + group +
                '}';
    }
}
