package com.java.course.universitytimetablemanager;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.course.universitytimetablemanager.conroller.CourseController;
import com.java.course.universitytimetablemanager.dto.CourseDTO;
import com.java.course.universitytimetablemanager.dto.GroupCourseDTO;
import com.java.course.universitytimetablemanager.dto.StudentCourseDTO;
import com.java.course.universitytimetablemanager.dto.TutorCourseDTO;
import com.java.course.universitytimetablemanager.model.Course;
import com.java.course.universitytimetablemanager.service.CourseService;
import com.java.course.universitytimetablemanager.service.GroupService;
import com.java.course.universitytimetablemanager.service.StudentService;
import com.java.course.universitytimetablemanager.service.TutorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private TutorService tutorService;
    @InjectMocks
    private CourseController courseController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "user")
    void shouldCreateNewCourse() throws Exception {

        mockMvc.perform(post("/courses/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", "1")
                        .param("courseName", "Mathematics")
                        .param("courseDescription", "MathCourse"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateCourse() throws Exception {
        mockMvc.perform(post("/courses/{courseId}/edit", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", "1")
                        .param("courseName", "Updated Mathe")
                        .param("courseDescription", "UpdatedDescription"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).updateCourse(any(CourseDTO.class));
    }


    @Test
    @WithMockUser(username = "user")
    void shouldDeleteCourse() throws Exception {
        mockMvc.perform(post("/courses/{courseId}/remove", 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldGetCourseList() throws Exception {

        List<Course> courses = List.of(new Course(1, "Mathe", "MatheCourse"),
                new Course(2, "Bio", "BiologyCourse"));

        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/courses")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("coursesPage"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attribute("courses", Matchers.hasSize(2)))
                .andExpect(model().attribute("courses", courses));
    }


    @Test
    @WithMockUser(username = "user")
    void shouldAddTutorToCourse() throws Exception {
        TutorCourseDTO tutorCourseDTO = new TutorCourseDTO();
        tutorCourseDTO.setTutorId(1);
        tutorCourseDTO.setCourseId(1);

        mockMvc.perform(post("/courses/add/tutor")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("tutorId", String.valueOf(tutorCourseDTO.getTutorId()))
                        .param("courseId", String.valueOf(tutorCourseDTO.getCourseId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).addTutorToCourse(tutorCourseDTO.getTutorId(), tutorCourseDTO.getCourseId());
    }


    @Test
    @WithMockUser(username = "user")
    void shouldDeleteTutorFromCourse() throws Exception {

        TutorCourseDTO tutorCourseDTO = new TutorCourseDTO();
        tutorCourseDTO.setCourseId(1);
        tutorCourseDTO.setTutorId(1);

        mockMvc.perform(post("/courses/remove/tutor")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", String.valueOf(tutorCourseDTO.getCourseId()))
                        .param("tutorId", String.valueOf(tutorCourseDTO.getTutorId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).removeTutorFromCourse(tutorCourseDTO.getCourseId(), tutorCourseDTO.getTutorId());
    }


    @Test
    @WithMockUser(username = "user")
    void shouldAddGroupToCourse() throws Exception {
        GroupCourseDTO groupCourseDTO = new GroupCourseDTO();
        groupCourseDTO.setCourseId(1);
        groupCourseDTO.setGroupId(1);

        mockMvc.perform(post("/courses/add/group")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", String.valueOf(groupCourseDTO.getCourseId()))
                        .param("groupId", String.valueOf(groupCourseDTO.getGroupId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).addGroupToCourse(groupCourseDTO.getCourseId(), groupCourseDTO.getGroupId());
    }


    @Test
    @WithMockUser(username = "user")
    void shouldRemoveGroupFromCourse() throws Exception {
        GroupCourseDTO groupCourseDTO = new GroupCourseDTO();
        groupCourseDTO.setCourseId(1);
        groupCourseDTO.setGroupId(1);

        mockMvc.perform(post("/courses/remove/group")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", String.valueOf(groupCourseDTO.getCourseId()))
                        .param("groupId", String.valueOf(groupCourseDTO.getGroupId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).removeGroupFromCourse(groupCourseDTO.getGroupId(), groupCourseDTO.getCourseId());
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void shouldAddStudentToCourse() throws Exception {
        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
        studentCourseDTO.setCourseId(1);
        studentCourseDTO.setStudentId(1);

        mockMvc.perform(post("/courses/add/student")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", String.valueOf(studentCourseDTO.getCourseId()))
                        .param("studentId", String.valueOf(studentCourseDTO.getStudentId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).addStudentToCourse(studentCourseDTO.getCourseId(), studentCourseDTO.getStudentId());
    }

    @Test
    @WithMockUser(username = "user", roles = "STAFF")
    void shouldRemoveStudentFromCourse() throws Exception {
        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
        studentCourseDTO.setCourseId(1);
        studentCourseDTO.setStudentId(1);

        mockMvc.perform(post("/courses/remove/student")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("courseId", String.valueOf(studentCourseDTO.getCourseId()))
                        .param("studentId", String.valueOf(studentCourseDTO.getStudentId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService, times(1)).removeStudentFromCourse(studentCourseDTO.getCourseId(), studentCourseDTO.getStudentId());
    }
}
