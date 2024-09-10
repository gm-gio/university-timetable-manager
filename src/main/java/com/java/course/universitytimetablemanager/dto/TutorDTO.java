package com.java.course.universitytimetablemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class TutorDTO {

    Integer tutorId;
    String tutorName;
}
