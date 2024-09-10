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
public class StudentGroupDTO {

    @NotNull(message = "Please select a student", groups = TransferGroup.class)
    private Integer studentId;

    @NotNull(message = "Please select a group", groups = AddOrRemoveGroup.class)
    private Integer groupId;

    @NotNull(message = "Please select a source group", groups = TransferGroup.class)
    private Integer sourceGroupId;

    @NotNull(message = "Please select a target group", groups = TransferGroup.class)
    private Integer targetGroupId;

    public interface AddOrRemoveGroup {}
    public interface TransferGroup {}
}
