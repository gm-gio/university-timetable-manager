package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dto.*;
import com.java.course.universitytimetablemanager.model.Schedule;
import com.java.course.universitytimetablemanager.service.AudienceService;
import com.java.course.universitytimetablemanager.service.GroupService;
import com.java.course.universitytimetablemanager.service.ScheduleService;
import com.java.course.universitytimetablemanager.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private static final String CREATE_FORM = "scheduleCreateForm";
    private static final String UPDATE_FORM = "scheduleUpdateForm";
    private static final String DELETE_FORM = "scheduleDeleteForm";

    private final ScheduleService scheduleService;
    private final TutorService tutorService;
    private final GroupService groupService;
    private final AudienceService audienceService;


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/new")
    public String getCreatePage(Model model) {
        model.addAttribute("scheduleDTO", new ScheduleDTO());
        return CREATE_FORM;
    }


    @PostMapping("/create")
    public String createSchedule(@Valid @ModelAttribute ScheduleDTO scheduleDTO, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            scheduleService.save(scheduleDTO);
            return "redirect:/schedules";
        }

        return CREATE_FORM;
    }


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/{scheduleId}/remove")
    public String getDeleteFormPage(@PathVariable Integer scheduleId, Model model) {
        Schedule schedule = scheduleService.findById(scheduleId);
        model.addAttribute("schedule", schedule);
        return DELETE_FORM;
    }


    @PostMapping("/{scheduleId}/remove")
    public String deleteSchedule(@PathVariable Integer scheduleId) {
        scheduleService.deleteById(scheduleId);
        return "redirect:/schedules";
    }


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/{scheduleId}/edit")
    public String getUpdateForm(@PathVariable Integer scheduleId, Model model) {
        Schedule schedule = scheduleService.findById(scheduleId);
        model.addAttribute("schedule", schedule);
        return UPDATE_FORM;
    }


    @PostMapping("/{scheduleId}/edit")
    public String updateSchedule(@PathVariable Integer scheduleId, @Valid @ModelAttribute("schedule") ScheduleDTO schedule) {
        scheduleService.update(schedule);
        return "redirect:/schedules";
    }


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/add/tutor/page")
    public String getAddTutorToSchedulePage(Model model) {
        model.addAttribute("tutors", tutorService.getAllTutors());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("tutorScheduleDTO", new TutorScheduleDTO());
        return "schedule-addTutorToSchedule";
    }


    @PostMapping("/add/tutor")
    public String addTutorToSchedule(@Valid @ModelAttribute TutorScheduleDTO tutorScheduleDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tutors", tutorService.getAllTutors());
            model.addAttribute("schedules", scheduleService.getAllSchedules());
            return "schedule-addTutorToSchedule";
        }
        scheduleService.addTutorToSchedule(tutorScheduleDTO.getTutorId(), tutorScheduleDTO.getScheduleId());
        return "redirect:/schedules";
    }


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/remove/tutor/page")
    public String getRemoveTutorFromSchedulePage(Model model) {
        model.addAttribute("tutors", tutorService.getAllTutors());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("tutorScheduleDTO", new TutorScheduleDTO());
        return "schedule-deleteTutorFromSchedulePage";
    }


    @PostMapping("/remove/tutor")
    public String getRemoveTutorFromSchedule(@Valid @ModelAttribute TutorScheduleDTO tutorScheduleDTO,
                                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tutors", tutorService.getAllTutors());
            model.addAttribute("schedule", scheduleService.getAllSchedules());
            return "schedule-deleteTutorFromSchedulePage";
        }
        scheduleService.removeTutorFromSchedule(tutorScheduleDTO.getTutorId(), tutorScheduleDTO.getScheduleId());
        return "redirect:/schedules";
    }


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/add/group/page")
    public String getAddGroupToSchedulePage(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("groupScheduleDTO", new GroupScheduleDTO());
        return "schedule-addGroupToSchedule";
    }


    @PostMapping("/add/group")
    public String addGroupToSchedule(@Valid @ModelAttribute GroupScheduleDTO groupScheduleDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("schedules", scheduleService.getAllSchedules());
            return "schedule-addGroupToSchedule";
        }
        scheduleService.addGroupToSchedule(groupScheduleDTO.getGroupId(), groupScheduleDTO.getScheduleId());
        return "redirect:/schedules";
    }

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/add/audience/page")
    public String getAddAudienceToSchedulePage(Model model){
        model.addAttribute("audiences", audienceService.getAllAudiences());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("audienceScheduleDTO", new AudienceScheduleDTO());
        return "schedule-addAudienceToSchedule";
    }

    @PostMapping("/add/audience")
    public String addAudienceToSchedule(@Valid @ModelAttribute AudienceScheduleDTO audienceScheduleDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("audiences", audienceService.getAllAudiences());
            model.addAttribute("schedules", scheduleService.getAllSchedules());
            return "schedule-addAudienceToSchedule";
        }
        scheduleService.addAudienceToSchedule(audienceScheduleDTO.getLocationId(), audienceScheduleDTO.getScheduleId());
        return "redirect:/schedules";
    }


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/remove/group/page")
    public String getRemoveGroupFromSchedulePage(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("groupScheduleDTO", new GroupScheduleDTO());
        return "schedule-deleteGroupFromSchedule";
    }

    @Transactional
    @PostMapping("/remove/group")
    public String getRemoveGroupFromSchedule(@Valid @ModelAttribute GroupScheduleDTO groupScheduleDTO,
                                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("schedules", scheduleService.getAllSchedules());
            return "schedule-deleteGroupFromSchedule";
        }
        scheduleService.removeGroupFromSchedule(groupScheduleDTO.getGroupId(), groupScheduleDTO.getScheduleId());
        return "redirect:/schedules";
    }

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("remove/audience/page")
    public String getRemoveAudienceFromSchedulePage(Model model){
        model.addAttribute("audiences", audienceService.getAllAudiences());
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("audienceScheduleDTO", new AudienceScheduleDTO());
        return "schedule-deleteAudienceFromSchedule";
    }

    @PostMapping("/remove/audience")
    public String getRemoveAudienceFromSchedule(@Valid @ModelAttribute AudienceScheduleDTO audienceScheduleDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("audiences", audienceService.getAllAudiences());
            model.addAttribute("scjedules", scheduleService.getAllSchedules());
            return "schedule-deleteAudienceFromSchedule";
        }
        scheduleService.removeAudienceFromSchedule(audienceScheduleDTO.getLocationId(), audienceScheduleDTO.getScheduleId());
        return "redirect:/schedules";
    }


    @Transactional
    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("/{scheduleId}/details")
    public String showScheduleDetails(@PathVariable Integer scheduleId, Model model) {
        Schedule schedule = scheduleService.findById(scheduleId);

        model.addAttribute("schedule", schedule);

        return "schedule-details";
    }

    @Transactional
    @RequestMapping("")
    public String getAllSchedules(Model model) {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "schedulesPage";
    }
}
