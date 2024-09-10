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
public class AudienceScheduleDTO {
    @NotNull(message = "Please select a Audience")
    Integer locationId;
    @NotNull(message = "Please select a Schedule")
    Integer scheduleId;

}
