package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.dto.UserDTO;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void createNewTutor(Tutor tutor);

    void deleteTutor(Tutor tutor);

    List<User> getAllUsers();

    boolean usernameExists(String username);

    @Transactional
    User registerNewUserAccount(UserDTO userDto) throws RuntimeException;
}
