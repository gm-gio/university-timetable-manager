package com.java.course.universitytimetablemanager;

import com.java.course.universitytimetablemanager.service.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class GeneratorRunner implements ApplicationRunner {
    private final AudienceGeneratorService audienceGeneratorService;
    private final CourseGeneratorService courseGeneratorService;
    private final GroupGeneratorService groupGeneratorService;
    private final ScheduleGeneratorService scheduleGeneratorService;
    private final UserGeneratorService userGeneratorService;

    public GeneratorRunner(AudienceGeneratorService audienceGeneratorService,
                           CourseGeneratorService courseGeneratorService,
                           GroupGeneratorService groupGeneratorService,
                           ScheduleGeneratorService scheduleGeneratorService,
                           UserGeneratorService userGeneratorService) {
        this.audienceGeneratorService = audienceGeneratorService;
        this.courseGeneratorService = courseGeneratorService;
        this.groupGeneratorService = groupGeneratorService;
        this.scheduleGeneratorService = scheduleGeneratorService;
        this.userGeneratorService = userGeneratorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        audienceGeneratorService.generateAudiencesIfNeed();
        courseGeneratorService.generateCoursesIfNeed();
        groupGeneratorService.generateGroupsIfNeed();
        scheduleGeneratorService.generateSchedulesIfNeed();
        userGeneratorService.generateStudentIfNeed();
        userGeneratorService.assignStudentsToGroups();
        userGeneratorService.generateTutorsIfNeed();
        userGeneratorService.assignTutorsToCourse();
    }
}
