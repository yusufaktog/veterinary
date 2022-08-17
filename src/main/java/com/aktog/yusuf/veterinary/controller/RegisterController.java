package com.aktog.yusuf.veterinary.controller;

import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/${api.version}")
public class RegisterController {
    private final PetOwnerService petOwnerService;
    @Value("${api.version}")
    private String apiVersion;

    public RegisterController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping("/register")
    public String getCreateOwnerForm(Model model) {
        model.addAttribute("owner", new CreatePetOwnerRequest());
        return "register";
    }

    @PostMapping("/register")
    public String createPetOwner(@Valid @ModelAttribute("createPetOwnerRequest") CreatePetOwnerRequest createPetOwnerRequest
) {
/*        if(result.hasErrors()){
            model.addAttribute("owner", createPetOwnerRequest);
            return "register";
        }*/
        petOwnerService.createPetOwner(createPetOwnerRequest);
        return "redirect:/" + apiVersion + "/register?success";
    }
}
