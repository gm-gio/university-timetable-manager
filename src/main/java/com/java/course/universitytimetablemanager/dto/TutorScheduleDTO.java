package com.java.course.universitytimetablemanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class TutorScheduleDTO {

    @NotNull(message = "Please select a tutor")
    Integer tutorId;

    @NotNull(message = "Please select a schedule")
    Integer scheduleId;
}
