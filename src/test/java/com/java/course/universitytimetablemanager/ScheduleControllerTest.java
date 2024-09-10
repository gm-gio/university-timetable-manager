package com.java.course.universitytimetablemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.course.universitytimetablemanager.conroller.ScheduleController;
import com.java.course.universitytimetablemanager.dto.*;
import com.java.course.universitytimetablemanager.model.*;
import com.java.course.universitytimetablemanager.service.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ScheduleService scheduleService;
    @MockBean
    private UserService userService;
    @MockBean
    private TutorService tutorService;
    @MockBean
    private GroupService groupService;

    @MockBean
    private AudienceService audienceService;
    @InjectMocks
    private ScheduleController scheduleController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "user")
    void shouldCreateSchedule() throws Exception {

        mockMvc.perform(post("/schedules/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("scheduleDate", "2024-08-01")
                        .param("lessonNum", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateSchedule() throws Exception {

        mockMvc.perform(post("/schedules/{scheduleId}/edit", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("scheduleDate", "2024-08-02")
                        .param("lessonNum", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).update(any(ScheduleDTO.class));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteSchedule() throws Exception {

        mockMvc.perform(post("/schedules/{scheduleId}/remove", 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));
    }


    @Test
    @WithMockUser(username = "user")
    void shouldGetAllSchedules() throws Exception {

        List<Schedule> allSchedules = Arrays.asList(
                new Schedule(LocalDate.now(), 1, new Tutor(1, "tutor1", "login", "passHash", UserRole.TUTOR), new Group(1, "GR-1"), new Audience(1, "loc-1"), new Course(1, "Math", "MathCourse")),
                new Schedule(LocalDate.now(), 2, new Tutor(2, "tutor2", "login2", "passHash2", UserRole.TUTOR), new Group(2, "GR-2"), new Audience(2, "loc-2"), new Course(2, "Bio", "BiologyCourse"))
        );

        when(scheduleService.getAllSchedules()).thenReturn(allSchedules);

        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedulesPage"))
                .andExpect(model().attributeExists("schedules"))
                .andExpect(model().attribute("schedules", Matchers.hasSize(2)))
                .andExpect(model().attribute("schedules", Matchers.containsInAnyOrder(
                        allSchedules.get(0), allSchedules.get(1)
                )));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldAddTutorToSchedule() throws Exception {

        TutorScheduleDTO tutorScheduleDTO = new TutorScheduleDTO();
        tutorScheduleDTO.setTutorId(1);
        tutorScheduleDTO.setScheduleId(1);

        mockMvc.perform(post("/schedules/add/tutor")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("tutorId", String.valueOf(tutorScheduleDTO.getTutorId()))
                        .param("scheduleId", String.valueOf(tutorScheduleDTO.getScheduleId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).addTutorToSchedule(tutorScheduleDTO.getTutorId(), tutorScheduleDTO.getScheduleId());
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteTutorFromSchedule() throws Exception {

        TutorScheduleDTO tutorScheduleDTO = new TutorScheduleDTO();
        tutorScheduleDTO.setTutorId(1);
        tutorScheduleDTO.setScheduleId(1);

        mockMvc.perform(post("/schedules/remove/tutor")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("tutorId", String.valueOf(tutorScheduleDTO.getTutorId()))
                        .param("scheduleId", String.valueOf(tutorScheduleDTO.getScheduleId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).removeTutorFromSchedule(tutorScheduleDTO.getTutorId(), tutorScheduleDTO.getScheduleId());
    }

    @Test
    @WithMockUser(username = "user")
    void shouldAddGroupToSchedule() throws Exception {

        GroupScheduleDTO groupScheduleDTO = new GroupScheduleDTO();
        groupScheduleDTO.setGroupId(1);
        groupScheduleDTO.setScheduleId(2);

        mockMvc.perform(post("/schedules/add/group")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("groupId", String.valueOf(groupScheduleDTO.getGroupId()))
                        .param("scheduleId", String.valueOf(groupScheduleDTO.getScheduleId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).addGroupToSchedule(groupScheduleDTO.getGroupId(), groupScheduleDTO.getScheduleId());
    }

    @Test
    @WithMockUser
    void shouldRemoveGroupFromSchedule() throws Exception {

        GroupScheduleDTO groupScheduleDTO = new GroupScheduleDTO();
        groupScheduleDTO.setGroupId(1);
        groupScheduleDTO.setScheduleId(2);

        mockMvc.perform(post("/schedules/remove/group")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("groupId", String.valueOf(groupScheduleDTO.getGroupId()))
                        .param("scheduleId", String.valueOf(groupScheduleDTO.getScheduleId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).removeGroupFromSchedule(groupScheduleDTO.getGroupId(), groupScheduleDTO.getScheduleId());
    }

    @Test
    @WithMockUser(username = "user")
    void shouldAddAudienceToSchedule() throws Exception {

        AudienceScheduleDTO audienceScheduleDTO = new AudienceScheduleDTO();
        audienceScheduleDTO.setScheduleId(1);
        audienceScheduleDTO.setLocationId(1);

        mockMvc.perform(post("/schedules/add/audience")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("scheduleId", String.valueOf(audienceScheduleDTO.getScheduleId()))
                        .param("locationId", String.valueOf(audienceScheduleDTO.getLocationId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).addAudienceToSchedule(audienceScheduleDTO.getScheduleId(), audienceScheduleDTO.getScheduleId());
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRemoveAudienceFromSchedule() throws Exception {

        AudienceScheduleDTO audienceScheduleDTO = new AudienceScheduleDTO();
        audienceScheduleDTO.setScheduleId(1);
        audienceScheduleDTO.setLocationId(1);

        mockMvc.perform(post("/schedules/remove/audience")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("scheduleId", String.valueOf(audienceScheduleDTO.getScheduleId()))
                        .param("locationId", String.valueOf(audienceScheduleDTO.getLocationId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedules"));

        verify(scheduleService, times(1)).removeAudienceFromSchedule(audienceScheduleDTO.getScheduleId(), audienceScheduleDTO.getScheduleId());
    }

}



