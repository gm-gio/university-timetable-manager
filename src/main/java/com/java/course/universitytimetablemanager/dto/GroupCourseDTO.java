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
public class GroupCourseDTO {
    @NotNull(message = "Please select a group")
    Integer groupId;
    @NotNull(message = "Please select a course")
    Integer courseId;
}
