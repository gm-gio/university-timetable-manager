package com.java.course.universitytimetablemanager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ScheduleDTO {

    private Integer scheduleId;

    private LocalDate scheduleDate;

    private Integer lessonNum;
}
