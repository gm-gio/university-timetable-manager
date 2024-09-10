package com.java.course.universitytimetablemanager.conroller;


import com.java.course.universitytimetablemanager.dto.CourseDTO;
import com.java.course.universitytimetablemanager.dto.GroupCourseDTO;
import com.java.course.universitytimetablemanager.dto.StudentCourseDTO;
import com.java.course.universitytimetablemanager.dto.TutorCourseDTO;
import com.java.course.universitytimetablemanager.model.Course;
import com.java.course.universitytimetablemanager.service.CourseService;
import com.java.course.universitytimetablemanager.service.GroupService;
import com.java.course.universitytimetablemanager.service.StudentService;
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
@RequestMapping("/courses")
public class CourseController {
    private static final String CREATE_FORM = "courseCreateForm";
    private static final String DELETE_FORM = "courseDeleteForm";
    private static final String UPDATE_FORM = "courseUpdateForm";

    private final CourseService courseService;
    private final StudentService studentService;
    private final GroupService groupService;
    private final TutorService tutorService;

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/new")
    public String getCreatePage(Model model) {

        model.addAttribute("courseDTO", new CourseDTO());
        return CREATE_FORM;
    }


    @PostMapping("/create")
    public String createCourse(@Valid @ModelAttribute CourseDTO courseDTO, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            courseService.save(courseDTO);
            return "redirect:/courses";
        }

        return CREATE_FORM;
    }

    @Secured("ADMIN")
    @GetMapping("/{courseId}/remove")
    public String getDeleteFormPage(@PathVariable Integer courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return DELETE_FORM;
    }


    @PostMapping("/{courseId}/remove")
    public String deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteById(courseId);
        return "redirect:/courses";
    }

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/{courseId}/edit")
    public String getUpdateForm(@PathVariable Integer courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return UPDATE_FORM;
    }

    @PostMapping("/{courseId}/edit")
    public String updateCourse(@PathVariable Integer courseId, @Valid @ModelAttribute("course") CourseDTO courseDTO, BindingResult result) {
        courseService.updateCourse(courseDTO);
        return "redirect:/courses";
    }


    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("")
    public String getCourseList(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "coursesPage";
    }

    @Secured("STAFF")
    @GetMapping("/add/tutor")
    public String getAddTutorToCoursePage(Model model) {
        model.addAttribute("tutors", tutorService.getAllTutors());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("tutorCourseDTO", new TutorCourseDTO());
        return "course-assignTutorToCoursePage";
    }

    @PostMapping("/add/tutor")
    public String assignTutorToCourse(@Valid @ModelAttribute TutorCourseDTO tutorCourseDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tutors,", tutorService.getAllTutors());
            model.addAttribute("courses", courseService.getAllCourses());
            return "course-assignTutorToCoursePage";
        }
        courseService.addTutorToCourse(tutorCourseDTO.getTutorId(), tutorCourseDTO.getCourseId());
        return "redirect:/courses";
    }


    @Secured("STAFF")
    @GetMapping("/remove/tutor")
    public String getDeleteTutorFromCoursePage(Model model) {
        model.addAttribute("tutors", tutorService.getAllTutors());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("tutorCourseDTO", new TutorCourseDTO());
        return "course-deleteTutorFromCoursePage";
    }

    @PostMapping("/remove/tutor")
    public String getDeleteTutorFromCourse(@Valid @ModelAttribute TutorCourseDTO tutorCourseDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tutors", tutorService.getAllTutors());
            model.addAttribute("courses", courseService.getAllCourses());
            return "course-deleteTutorFromCoursePage";
        }
        courseService.removeTutorFromCourse(tutorCourseDTO.getTutorId(), tutorCourseDTO.getCourseId());
        return "redirect:/courses";
    }

    @Secured("STAFF")
    @GetMapping("/add/group")
    public String getAddGroupToCoursePage(Model model) {

        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("groupCourseDTO", new GroupCourseDTO());

        return "/course-assignGroupToCoursePage";
    }

    @PostMapping("/add/group")
    public String assignGroupToCourse(@Valid @ModelAttribute GroupCourseDTO groupCourseDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("courses", courseService.getAllCourses());
            return "course-assignGroupToCoursePage";
        }
        courseService.addGroupToCourse(groupCourseDTO.getGroupId(), groupCourseDTO.getCourseId());
        return "redirect:/courses";
    }


    @Secured("STAFF")
    @GetMapping("/remove/group")
    public String getDeleteGroupFromCoursePage(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("groupCourseDTO", new GroupCourseDTO());
        return "/course-deleteGroupFromCoursePage";
    }

    @PostMapping("/remove/group")
    public String deleteGroupFromCourse(@Valid @ModelAttribute GroupCourseDTO groupCourseDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("courses", courseService.getAllCourses());
            return "/course-deleteGroupFromCoursePage";
        }
        courseService.removeGroupFromCourse(groupCourseDTO.getGroupId(), groupCourseDTO.getCourseId());
        return "redirect:/courses";
    }

    @Transactional
    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("/{courseId}/details")
    public String showCourseDetails(@PathVariable Integer courseId, Model model) {

        Course course = courseService.findById(courseId);

        model.addAttribute("course", course);

        return "course-details";

    }

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/add/student")
    public String getAddStudentToCourseForm(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("studentCourseDTO", new StudentCourseDTO());
        return "course-addStudentToCourse";
    }

    @PostMapping("/add/student")
    public String addStudentToCourse(@Valid @ModelAttribute StudentCourseDTO studentCourseDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            return "course-addStudentToCourse";
        }

        courseService.addStudentToCourse(studentCourseDTO.getStudentId(), studentCourseDTO.getCourseId());
        return "redirect:/courses";
    }

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/remove/student")
    public String getRemoveStudentFromCourseForm(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("studentCourseDTO", new StudentCourseDTO());
        return "course-removeStudentFromCourse";
    }

    @PostMapping("/remove/student")
    public String removeStudentFromCourse(@Valid @ModelAttribute StudentCourseDTO studentCourseDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            return "course-removeStudentFromCourse";
        }
        courseService.removeStudentFromCourse(studentCourseDTO.getStudentId(), studentCourseDTO.getCourseId());
        return "redirect:/courses";
    }
}
