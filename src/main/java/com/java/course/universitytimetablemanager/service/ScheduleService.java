package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.dto.ScheduleDTO;
import com.java.course.universitytimetablemanager.model.Schedule;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduleService {


    @Transactional
    Schedule save(ScheduleDTO scheduleDTO);

    void deleteSchedule(Schedule schedule);

    List<Schedule> getAllSchedules();

    @Transactional
    void deleteById(Integer id);

    Schedule findById(Integer scheduleId);

    @Transactional
    void update(ScheduleDTO updatedSchedule);


    @Transactional
    void addTutorToSchedule(Integer tutorId, Integer scheduleId);

    @Transactional
    void addGroupToSchedule(Integer groupId, Integer scheduleId);

    @Transactional
    void removeTutorFromSchedule(Integer groupId, Integer scheduleId);

    @Transactional
    void removeGroupFromSchedule(Integer groupId, Integer scheduleId);

    @Transactional
    void addAudienceToSchedule(Integer locationId, Integer scheduleId);

    @Transactional
    void removeAudienceFromSchedule(Integer locationId, Integer scheduleId);
}
