package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequiredArgsConstructor
public class WebController {

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }


    @GetMapping("/user/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "/login/userRegister";
    }

    @GetMapping("/user/login")
    public String getUserLogin() {
        return "/login/userLogin";
    }

    @GetMapping("/admin/panel")
    public String adminPanel() {
        return "/admin/admin-panel";
    }

    @GetMapping("/admin/assignRole")
    public String assignRole() {
        return "#";
    }

}
