package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dao.StudentRepository;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("")
    public String getStudents(Model model) {
        List<Student> students = studentService.findAllStudents();

        model.addAttribute("students", students);

        return "studentsPage";
    }

    @Transactional
    @Secured({"ADMIN", "USER", "TUTOR", "STUDENT", "STAFF"})
    @GetMapping("/{studentId}/details")
    public String showStudentDetails(@PathVariable Integer studentId, Model model) {
        Student student = studentService.findStudentWithGroupsByUserId(studentId);

        model.addAttribute("student", student);
        return "student-details";
    }

}
