package com.java.course.universitytimetablemanager;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.course.universitytimetablemanager.conroller.GroupController;
import com.java.course.universitytimetablemanager.dto.GroupDTO;
import com.java.course.universitytimetablemanager.dto.StudentCourseDTO;
import com.java.course.universitytimetablemanager.dto.StudentGroupDTO;
import com.java.course.universitytimetablemanager.model.Group;
import com.java.course.universitytimetablemanager.service.GroupService;
import com.java.course.universitytimetablemanager.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
public class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    @MockBean
    private StudentService studentService;
    @InjectMocks
    private GroupController groupController;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @WithMockUser(username = "user")
    void shouldCreateGroup() throws Exception {

        mockMvc.perform(post("/groups/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("groupId", "1")
                        .param("groupName", "Group 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }


    @Test
    @WithMockUser(username = "user")
    void shouldDeleteGroup() throws Exception {

        mockMvc.perform(post("/groups/{groupId}/remove", 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }


    @Test
    @WithMockUser(username = "user")
    void shouldGetGroupList() throws Exception {
        List<Group> groups = List.of(new Group(1, "Test1"), new Group(2, "Test2"));

        when(groupService.getAllGroups()).thenReturn(groups);


        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("groupPage"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", Matchers.hasSize(2)))
                .andExpect(model().attribute("groups", groups));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateGroup() throws Exception {

        mockMvc.perform(post("/groups/{groupId}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("groupId", "1")
                        .param("groupName", "updatedGroup"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService, times(1)).updateGroup(any(GroupDTO.class));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldAddStudentToGroup() throws Exception {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupId(1);
        studentGroupDTO.setStudentId(1);

        mockMvc.perform(post("/groups/add/student")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("groupId", String.valueOf(studentGroupDTO.getGroupId()))
                        .param("studentId", String.valueOf(studentGroupDTO.getStudentId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService, times(1)).addStudentToGroup(studentGroupDTO.getGroupId(), studentGroupDTO.getStudentId());
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRemoveStudentFromGroup() throws Exception {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupId(1);
        studentGroupDTO.setStudentId(1);

        mockMvc.perform(post("/groups/remove/student")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("groupId", String.valueOf(studentGroupDTO.getGroupId()))
                        .param("studentId", String.valueOf(studentGroupDTO.getStudentId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService, times(1)).removeStudentFromGroup(studentGroupDTO.getGroupId(),
                studentGroupDTO.getStudentId());
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldTransferStudentAndRedirect() throws Exception {

        mockMvc.perform(post("/groups/transfer/student")
                        .with(csrf())
                        .param("studentId", "1")
                        .param("sourceGroupId", "1")
                        .param("targetGroupId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }
}
