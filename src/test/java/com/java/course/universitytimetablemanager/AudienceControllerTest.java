package com.java.course.universitytimetablemanager;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.course.universitytimetablemanager.conroller.AudienceController;
import com.java.course.universitytimetablemanager.dto.AudienceDTO;
import com.java.course.universitytimetablemanager.model.Audience;
import com.java.course.universitytimetablemanager.service.AudienceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import static org.mockito.Mockito.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AudienceController.class)
public class AudienceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudienceService audienceService;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @WithMockUser(username = "user")
    void shouldCreateNewAudience() throws Exception {

        mockMvc.perform(post("/audiences/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("locationId", "1")
                        .param("audienceName", "Location12"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/audiences"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateAudience() throws Exception {

        mockMvc.perform(post("/audiences/{audienceId}/edit", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("locationId", "1")
                        .param("audienceName", "Audience123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/audiences"));

        verify(audienceService, times(1)).updateAudience(any(AudienceDTO.class));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteAudience() throws Exception {

        mockMvc.perform(post("/audiences/{audienceId}/remove", 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/audiences"));

    }

    @Test
    @WithMockUser(username = "user")
    void shouldGetAudienceList() throws Exception {

        List<Audience> allAudiences = List.of(new Audience(1, "Audience2"), new Audience(2, "Audience2"));

        when(audienceService.getAllAudiences()).thenReturn(allAudiences);

        mockMvc.perform(get("/audiences"))
                .andExpect(status().isOk())
                .andExpect(view().name("audiencesPage"))
                .andExpect(model().attributeExists("audiences"))
                .andExpect(model().attribute("audiences", Matchers.hasSize(2)))
                .andExpect(model().attribute("audiences", allAudiences));
    }
}
