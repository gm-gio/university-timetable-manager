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
public class CourseDTO {


    private Integer courseId;

    @NotBlank(message = "Course name must not be blank")
    @Size(min = 5, max = 20, message = "Course's name must be between 5 and 20 characters.")
    private String courseName;

    @NotBlank(message = "Group name must not be blank")
    @Size(min = 5, max = 250, message = "Course's description must be between 5 and 250 characters.")
    private String courseDescription;
}
