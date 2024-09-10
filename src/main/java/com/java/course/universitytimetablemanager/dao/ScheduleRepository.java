package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
