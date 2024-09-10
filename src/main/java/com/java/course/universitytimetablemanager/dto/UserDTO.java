package com.java.course.universitytimetablemanager.dto;

import com.java.course.universitytimetablemanager.model.UserRole;
import com.java.course.universitytimetablemanager.model.UserType;
import com.java.course.universitytimetablemanager.validator.PasswordMatches;
import com.java.course.universitytimetablemanager.validator.ValidUserLogin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@PasswordMatches
public class UserDTO {


    private Integer id;

    @NotBlank(message = "User  name must not be blank")
    @Size(min = 5, max = 20, message = "User name must be between 5 and 20 characters.")
    private String username;

    @ValidUserLogin
    private String userLogin;


    private String password;


    private String confirmPassword;

    private UserRole userRole;


    private UserType userType;

    private UserRole role;
}
