package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.AudienceRepository;
import com.java.course.universitytimetablemanager.dao.GroupRepository;
import com.java.course.universitytimetablemanager.dao.ScheduleRepository;
import com.java.course.universitytimetablemanager.dao.TutorRepository;
import com.java.course.universitytimetablemanager.dto.ScheduleDTO;
import com.java.course.universitytimetablemanager.model.Audience;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.model.Schedule;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TutorRepository tutorRepository;
    private final GroupRepository groupRepository;
    private final AudienceRepository audienceRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, TutorRepository tutorRepository, GroupRepository groupRepository, AudienceRepository audienceRepository) {
        this.scheduleRepository = scheduleRepository;
        this.tutorRepository = tutorRepository;
        this.groupRepository = groupRepository;
        this.audienceRepository = audienceRepository;
    }


    @Override
    @Transactional
    public Schedule save(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setScheduleDate(scheduleDTO.getScheduleDate());
        schedule.setLessonNum(scheduleDTO.getLessonNum());

        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public Schedule findById(Integer scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    @Override
    @Transactional
    public void update(ScheduleDTO updatedSchedule) {
        Schedule existsSchedule = scheduleRepository.findById(updatedSchedule.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + updatedSchedule.getScheduleId()));
        existsSchedule.setScheduleDate(updatedSchedule.getScheduleDate());
        existsSchedule.setLessonNum(updatedSchedule.getLessonNum());

    }

    @Override
    @Transactional
    public void addTutorToSchedule(Integer tutorId, Integer scheduleId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        schedule.setTutor(tutor);
        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void addGroupToSchedule(Integer groupId, Integer scheduleId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        schedule.setGroup(group);
        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void addAudienceToSchedule(Integer locationId, Integer scheduleId){
        Audience audience = audienceRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Audience not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        schedule.setAudience(audience);
        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void removeTutorFromSchedule(Integer groupId, Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        schedule.setTutor(null);
        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void removeGroupFromSchedule(Integer groupId, Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        schedule.setGroup(null);
        scheduleRepository.save(schedule);
    }



    @Override
    @Transactional
    public void removeAudienceFromSchedule(Integer locationId, Integer scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        schedule.setAudience(null);
        scheduleRepository.save(schedule);
    }
}
