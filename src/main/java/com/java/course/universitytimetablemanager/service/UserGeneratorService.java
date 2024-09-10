package com.java.course.universitytimetablemanager.service;

public interface UserGeneratorService {
    void generateStudentIfNeed();

    void assignStudentsToGroups();

    void generateTutorsIfNeed();

    void assignTutorsToCourse();
}
