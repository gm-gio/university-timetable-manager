package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dto.UserDTO;
import com.java.course.universitytimetablemanager.model.User;
import com.java.course.universitytimetablemanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public String getUserList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-list";
    }


    @PostMapping("/user/register")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDTO userDTO,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/login/userRegister";
        }

        try {
            User registered = userService.registerNewUserAccount(userDTO);
            return "redirect:/login/userLogin";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "/login/userRegister";
        }
    }
}
