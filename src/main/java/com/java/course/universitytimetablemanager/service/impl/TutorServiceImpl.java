package com.java.course.universitytimetablemanager.service.impl;

import com.java.course.universitytimetablemanager.dao.TutorRepository;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.service.TutorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    @Transactional
    public List<Tutor> getAllTutors(){
        return tutorRepository.findAll();
    }

    @Override
    @Transactional
    public Tutor getTutorsWithCourses(Integer tutorId){
        return tutorRepository.findTutorWithCoursesByUserId(tutorId)
                .orElseThrow(()-> new EntityNotFoundException("Tutor not found"));
    }
    @Override
    public Optional<Tutor> findById(Integer tutorId){
        return tutorRepository.findById(tutorId);
    }
}
