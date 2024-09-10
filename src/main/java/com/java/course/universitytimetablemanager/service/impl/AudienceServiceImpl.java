package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.AudienceRepository;
import com.java.course.universitytimetablemanager.dto.AudienceDTO;
import com.java.course.universitytimetablemanager.model.Audience;
import com.java.course.universitytimetablemanager.service.AudienceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AudienceServiceImpl implements AudienceService {
    private final AudienceRepository audienceRepository;

    public AudienceServiceImpl(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    private boolean audienceNameExists(String audienceName) {
        return audienceRepository.findAudienceByAudienceName(audienceName).isPresent();
    }

    @Override
    @Transactional
    public Audience save(AudienceDTO audienceDTO) {
        if (audienceNameExists(audienceDTO.getAudienceName())) {
            throw new RuntimeException("Audience with name " + audienceDTO.getAudienceName() + " already exists.");
        }

        Audience audience = new Audience();
        audience.setAudienceName(audienceDTO.getAudienceName());

        return audienceRepository.save(audience);
    }

    @Override
    @Transactional
    public void deleteById(Integer locationId) {
        audienceRepository.deleteById(locationId);
    }

    @Override
    @Transactional
    public Audience finById(Integer locationId) {
        return audienceRepository.findById(locationId).orElseThrow(null);
    }

    @Override
    @Transactional
    public void updateAudience(AudienceDTO audienceDTO) {

        Audience existingAudience = audienceRepository.findById(audienceDTO.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Audience not found with id: " + audienceDTO.getAudienceName()));
        existingAudience.setAudienceName(audienceDTO.getAudienceName());

        audienceRepository.save(existingAudience);
    }

    @Override
    public List<Audience> getAllAudiences() {
        return audienceRepository.findAll();
    }

}
