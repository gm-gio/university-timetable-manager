package com.java.course.universitytimetablemanager.dao;

import com.java.course.universitytimetablemanager.model.Audience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AudienceRepository extends JpaRepository<Audience, Integer> {

    Optional<Audience> findAudienceByAudienceName(String audienceName);
}
