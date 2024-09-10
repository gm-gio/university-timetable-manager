package com.java.course.universitytimetablemanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AudienceDTO {


    private Integer locationId;


    @NotBlank(message = "Audience name must not be blank")
    @Size(min = 5, max = 50, message = "Audience name must be between 5 and 50 characters.")
    private String audienceName;
}
