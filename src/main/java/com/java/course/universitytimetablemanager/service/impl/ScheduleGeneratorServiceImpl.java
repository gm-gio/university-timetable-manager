package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.*;
import com.java.course.universitytimetablemanager.model.*;
import com.java.course.universitytimetablemanager.service.ScheduleGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleGeneratorServiceImpl implements ScheduleGeneratorService {
    private final ScheduleRepository scheduleRepository;
    private final TutorRepository tutorRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final AudienceRepository audienceRepository;

    @Autowired
    public ScheduleGeneratorServiceImpl(ScheduleRepository scheduleRepository, TutorRepository tutorRepository,
                                        GroupRepository groupRepository, CourseRepository courseRepository, AudienceRepository audienceRepository) {
        this.scheduleRepository = scheduleRepository;
        this.tutorRepository = tutorRepository;
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.audienceRepository = audienceRepository;
    }


    @Override
    public void generateSchedulesIfNeed() {
        if (scheduleRepository.count() == 0) {
            List<Schedule> schedules = new ArrayList<>();

            schedules.add(new Schedule(LocalDate.now(), 1,
                                tutorRepository.findById(1).orElse(null),
                                groupRepository.findById(1).orElse(null),
                    audienceRepository.findById(1).orElse(null),
                    courseRepository.findById(1).orElse(null)));

            schedules.add(new Schedule(LocalDate.now().plusDays(1), 2,
                                tutorRepository.findById(2).orElse(null),
                                groupRepository.findById(2).orElse(null),
                    audienceRepository.findById(2).orElse(null),
                    courseRepository.findById(2).orElse(null)));

            schedules.add(new Schedule(LocalDate.now().plusDays(2), 3,
                                tutorRepository.findById(3).orElse(null),
                                groupRepository.findById(3).orElse(null),
                    audienceRepository.findById(3).orElse(null),
                    courseRepository.findById(3).orElse(null)));

            scheduleRepository.saveAll(schedules);
        }
    }
}
