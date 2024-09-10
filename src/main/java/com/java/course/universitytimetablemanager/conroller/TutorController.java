package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dao.TutorRepository;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tutors")
public class TutorController {

    private final TutorService tutorService;


    @GetMapping("")
    public String getTutors(Model model){
        List<Tutor> tutors = tutorService.getAllTutors();

        model.addAttribute("tutors", tutors);

        return "tutorList";
    }

    @Transactional
    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("/{tutorId}/details")
    public  String showTutorDetails(@PathVariable Integer tutorId, Model model){
        Tutor tutor = tutorService.getTutorsWithCourses(tutorId);
        model.addAttribute("tutor", tutor);
        return "tutor-details";
    }
}
