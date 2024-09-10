package com.java.course.universitytimetablemanager.service.impl;


import com.java.course.universitytimetablemanager.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        return userRepository.findByUserLogin(userLogin)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + userLogin));
    }
}
