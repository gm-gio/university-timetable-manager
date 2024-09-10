package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.AudienceRepository;
import com.java.course.universitytimetablemanager.model.Audience;
import com.java.course.universitytimetablemanager.service.AudienceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AudienceGeneratorServiceImpl implements AudienceGeneratorService {
    private final AudienceRepository audienceRepository;

    @Autowired
    public AudienceGeneratorServiceImpl(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    @Override
    public void generateAudiencesIfNeed() {
        if (audienceRepository.count() == 0) {
            List<Audience> audiences = new ArrayList<>();
            audiences.add(new Audience("Audience 1"));
            audiences.add(new Audience("Audience 2"));
            audiences.add(new Audience("Audience 3"));
            audienceRepository.saveAll(audiences);
        }
    }
}
