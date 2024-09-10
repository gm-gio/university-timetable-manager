package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.TutorRepository;
import com.java.course.universitytimetablemanager.dao.UserRepository;
import com.java.course.universitytimetablemanager.dto.UserDTO;
import com.java.course.universitytimetablemanager.model.*;
import com.java.course.universitytimetablemanager.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final TutorRepository tutorRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(TutorRepository tutorRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tutorRepository = tutorRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createNewTutor(Tutor tutor) {
        tutorRepository.save(tutor);
    }

    @Override
    public void deleteTutor(Tutor tutor) {
        tutorRepository.delete(tutor);
    }


    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUserName(username).isPresent();
    }

    @Override
    @Transactional
    public User registerNewUserAccount(UserDTO userDto) throws RuntimeException {
        if (usernameExists(userDto.getUsername())) {
            throw new RuntimeException("There is an account with that username: " + userDto.getUsername());
        }

        User user = new User();
        user.setUserName(userDto.getUsername());
        user.setUserLogin(userDto.getUserLogin());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(UserRole.USER);
        user.setUserType(UserType.USER);

        return userRepository.save(user);
    }
}
