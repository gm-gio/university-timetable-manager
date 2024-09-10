package com.java.course.universitytimetablemanager.conroller;

import com.java.course.universitytimetablemanager.dto.AudienceDTO;
import com.java.course.universitytimetablemanager.model.Audience;
import com.java.course.universitytimetablemanager.service.AudienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("audiences")
public class AudienceController {
    private static final String CREATE_FORM = "audienceCreateForm";
    private static final String DELETE_FORM = "audienceDeleteForm";
    private static final String UPDATE_FORM = "audienceUpdateForm";


    private final AudienceService audienceService;

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/new")
    public String getCreatePage(Model model) {

        model.addAttribute("audienceDTO", new AudienceDTO());
        return CREATE_FORM;
    }


    @PostMapping("/create")
    public String createAudience(@Valid @ModelAttribute AudienceDTO audienceDTO, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            audienceService.save(audienceDTO);
            return "redirect:/audiences";
        }

        return CREATE_FORM;
    }

    @Secured("ADMIN")
    @GetMapping("/{audienceId}/remove")
    public String getDeleteFormPage(@PathVariable Integer audienceId, Model model) {
        Audience audience = audienceService.finById(audienceId);
        model.addAttribute("audience", audience);
        return DELETE_FORM;
    }


    @PostMapping("/{audienceId}/remove")
    public String deleteAudience(@PathVariable Integer audienceId) {
        audienceService.deleteById(audienceId);
        return "redirect:/audiences";
    }

    @Secured({"ADMIN", "STAFF"})
    @GetMapping("/{audienceId}/edit")
    public String getUpdateForm(@PathVariable Integer audienceId, Model model) {
        Audience audience = audienceService.finById(audienceId);
        model.addAttribute("audience", audience);
        return UPDATE_FORM;
    }

    @PostMapping("/{audienceId}/edit")
    public String updateAudience(@PathVariable Integer audienceId, @Valid @ModelAttribute("audience") AudienceDTO audienceDTO, BindingResult result) {
        audienceService.updateAudience(audienceDTO);
        return "redirect:/audiences";
    }

    @RequestMapping("")
    public String getAudienceList(Model model) {
        List<Audience> audiences = audienceService.getAllAudiences();
        model.addAttribute("audiences", audiences);
        return "audiencesPage";
    }
}
