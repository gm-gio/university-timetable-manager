package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);

    Optional<User> findByUserLogin(String userLogin);
}
