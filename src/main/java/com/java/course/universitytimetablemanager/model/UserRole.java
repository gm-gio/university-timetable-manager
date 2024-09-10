package com.java.course.universitytimetablemanager.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    STUDENT, TUTOR, ADMIN, STAFF, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
