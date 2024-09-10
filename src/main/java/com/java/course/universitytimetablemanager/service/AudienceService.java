package com.java.course.universitytimetablemanager.service;

import com.java.course.universitytimetablemanager.dto.AudienceDTO;
import com.java.course.universitytimetablemanager.model.Audience;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AudienceService {
    @Transactional
    Audience save(AudienceDTO audienceDTO);

    @Transactional
    void deleteById(Integer locationId);

    @Transactional
    Audience finById(Integer locationId);

    @Transactional
    void updateAudience(AudienceDTO audienceDTO);

    List<Audience> getAllAudiences();
}
