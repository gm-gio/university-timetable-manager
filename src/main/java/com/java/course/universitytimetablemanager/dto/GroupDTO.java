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
public class GroupDTO {

    private Integer groupId;


    @NotBlank(message = "Group name must not be blank")
    @Size(min = 5, max = 250, message = "Group's name must be between 5 and 250 characters.")
    private String groupName;
}
