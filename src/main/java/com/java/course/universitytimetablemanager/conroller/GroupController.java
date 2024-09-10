package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dto.GroupDTO;
import com.java.course.universitytimetablemanager.dto.StudentGroupDTO;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.service.GroupService;
import com.java.course.universitytimetablemanager.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private static final String CREATE_FORM = "groupCreateForm";
    private static final String DELETE_FORM = "groupDeleteForm";
    private static final String UPDATE_FORM = "groupUpdateForm";
    private static final String ASSIGN_STUDENT_FORM = "group-assignStudentToGroup";
    private static final String REMOVE_STUDENT_FORM = "group-removeStudentFromGroup";

    private final GroupService groupService;
    private final StudentService studentService;


    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/new")
    public String getGroupCreateFormPage(Model model) {
        model.addAttribute("groupDTO", new GroupDTO());
        return CREATE_FORM;
    }


    @PostMapping("/create")
    public String createGroup(@Valid @ModelAttribute GroupDTO groupDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            groupService.save(groupDTO);
            return "redirect:/groups";
        }

        model.addAttribute("groupDTO", groupDTO);
        model.addAttribute("errors", result.getAllErrors());

        return CREATE_FORM;
    }

    @Secured("ADMIN")
    @GetMapping("/{groupId}/remove")
    public String getGroupDeleteFormPage(@PathVariable Integer groupId, Model model) {
        Group group = groupService.findById(groupId);
        model.addAttribute("group", group);
        return DELETE_FORM;
    }

    @Secured("ADMIN")
    @PostMapping("/{groupId}/remove")
    public String deleteGroup(@PathVariable Integer groupId) {
        groupService.deleteById(groupId);
        return "redirect:/groups";
    }

    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("/{groupId}/edit")
    public String getGroupUpdateForm(@PathVariable Integer groupId, Model model) {
        Group group = groupService.findById(groupId);
        model.addAttribute("group", group);
        return UPDATE_FORM;
    }


    @PostMapping("/{groupId}")
    public String updateGroup(@PathVariable Integer groupId, @Valid @ModelAttribute("group") GroupDTO groupDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "groupPage";
        }
        groupService.updateGroup(groupDTO);
        return "redirect:/groups";
    }

    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("")
    public String getAllGroups(Model model) {
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "groupPage";
    }

    @Secured("ADMIN")
    @GetMapping("/add/student/page")
    public String getAddStudentToGroupForm(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("studentGroupDTO", new StudentGroupDTO());
        return ASSIGN_STUDENT_FORM;
    }


    @PostMapping("/add/student")
    public String addStudentToGroup(@Valid @ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {

            model.addAttribute("students", studentService.findAllStudents());
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("errors", result.getAllErrors());
            return ASSIGN_STUDENT_FORM;
        }
        groupService.addStudentToGroup(studentGroupDTO.getStudentId(), studentGroupDTO.getGroupId());

        return "redirect:/groups";
    }

    @Secured("ADMIN")
    @GetMapping("/remove/student/page")
    public String getDeleteStudentFromGroupForm(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("studentGroupDTO", new StudentGroupDTO());
        return REMOVE_STUDENT_FORM;
    }


    @PostMapping("/remove/student")
    public String deleteStudent(@Valid @ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAllStudents());
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("errors", result.getAllErrors());
            return REMOVE_STUDENT_FORM;
        }
        groupService.removeStudentFromGroup(studentGroupDTO.getStudentId(), studentGroupDTO.getGroupId());
        return "redirect:/groups";
    }

    @Transactional
    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("/{groupId}/details")
    public String showGroupDetails(@PathVariable Integer groupId, Model model) {
        Group group = groupService.findGroupByStudentsByGroupId(groupId);

        model.addAttribute("group", group);

        return "group-details";
    }

    @Transactional
    @Secured("ADMIN")
    @GetMapping("/transfer/student")
    public String getTransferStudentForm(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("studentGroupDTO", new StudentGroupDTO());
        return "group-transferStudents";
    }


    @Transactional
    @PostMapping("/transfer/student")
    public String transferStudent(
            @Validated(StudentGroupDTO.TransferGroup.class) @ModelAttribute StudentGroupDTO studentGroupDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAllStudents());
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("errors", result.getAllErrors());
            return "group-transferStudents";
        }

        Integer userId = studentGroupDTO.getStudentId();
        Integer sourceGroupId = studentGroupDTO.getSourceGroupId();
        Integer targetGroupId = studentGroupDTO.getTargetGroupId();
        groupService.transferStudentBetweenGroups(userId, sourceGroupId, targetGroupId);

        return "redirect:/groups";
    }

}
