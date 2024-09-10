package com.java.course.universitytimetablemanager;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.course.universitytimetablemanager.conroller.UserController;
import com.java.course.universitytimetablemanager.model.Student;
import com.java.course.universitytimetablemanager.model.Tutor;
import com.java.course.universitytimetablemanager.model.User;
import com.java.course.universitytimetablemanager.model.UserRole;
import com.java.course.universitytimetablemanager.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @WithMockUser(username = "user")
    void shouldGetUsersList() throws Exception {
        Student student = new Student(1, "student1", "login", "passHash", UserRole.STUDENT);
        Tutor tutor = new Tutor(2, "tutor2", "login2", "passHash2", UserRole.TUTOR);
        List<User> users = Arrays.asList(student, tutor);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users-list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", Matchers.hasSize(2)))
                .andExpect(model().attribute("users", Matchers.contains(student, tutor)));
    }
}
