package com.aktog.yusuf.veterinary.controller;

import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/${api.version}/owner")
public class PetOwnerController {
    @Value("${api.version}")
    private String apiVersion;

    private final PetOwnerService petOwnerService;

    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping
    public String getPetOwnerList(Model model) {
        model.addAttribute("owners", petOwnerService.getPetOwnerDtoList());
        return "owners";
    }

    @GetMapping("/create-owner")
    public String getCreateOwnerForm(Model model) {
        model.addAttribute("owner", new CreatePetOwnerRequest());
        return "create-owner";

    }

    @PostMapping("/create-owner")
    public String createPetOwner(@ModelAttribute("createPetOwnerRequest") CreatePetOwnerRequest createPetOwnerRequest) {
        petOwnerService.createPetOwner(createPetOwnerRequest);
        return "redirect:/" + apiVersion + "/owner";
    }

    @GetMapping("/update-owner/{id}")
    public String getUpdateOwnerForm(@PathVariable String id, Model model) {
        model.addAttribute("owner", petOwnerService.getPetOwnerById(id));
        return "update-owner";
    }

    @PostMapping("/update-owner/{id}")
    public String updateOwner(@PathVariable String id,
                              @ModelAttribute("updatePetOwnerRequest")UpdatePetOwnerRequest updatePetOwnerRequest){
        petOwnerService.updatePetOwner(id,updatePetOwnerRequest);
        return "redirect:/" + apiVersion + "/owner";
    }

    @GetMapping("/delete-owner/{id}")
    public String deleteOwner(@PathVariable String id) {
        petOwnerService.deletePetOwner(id);
        return "redirect:/" + apiVersion + "/owner";
    }
}
