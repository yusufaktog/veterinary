package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.request.create.CreatePetRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetRequest;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import com.aktog.yusuf.veterinary.service.PetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/${api.version}/pet")
public class PetController {

    @Value("${api.version}")
    private String apiVersion;

    private final PetService petService;
    private final PetOwnerService petOwnerService;

    public PetController(PetService petService, PetOwnerService petOwnerService) {
        this.petService = petService;
        this.petOwnerService = petOwnerService;
    }

    @GetMapping
    public String getPetList(Model model) {
        model.addAttribute("pets", petService.getPetDtoList());
        return "pets";
    }

    @GetMapping("/search")
    public String filterPets(Model model, @RequestParam String query) {
        model.addAttribute("pets", petService.doFilter(query));
        return "pets";
    }

    @GetMapping("/create-pet")
    public String getCreatePetForm(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("pet", new CreatePetRequest());
        String id = petOwnerService.findByEmail(currentUser.getUsername()).orElse(new PetOwner()).getId();
        model.addAttribute("ownerId", id);
        return "create-pet";
    }

    @PostMapping("/create-pet/{ownerId}")
    public String createPet(@PathVariable String ownerId,
                            @Valid @ModelAttribute CreatePetRequest createPetRequest,
                            BindingResult result) {
        petService.createPet(ownerId, createPetRequest);

        return "redirect:/" + apiVersion + "/pet";
    }

    @GetMapping("update-pet/{id}")
    public String getUpdatePetForm(@PathVariable String id, Model model) {
        model.addAttribute("pet", petService.getPetById(id));
        return "update-pet";
    }

    @PutMapping("/update-pet/{id}")
    public String updatePet(@PathVariable String id,
                            @ModelAttribute @Valid UpdatePetRequest updatePetRequest) {
        petService.updatePet(id, updatePetRequest);
        return "redirect:/" + apiVersion + "/pet";

    }

    @GetMapping("/delete-pet/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePet(@PathVariable String id) {
        petService.deletePetById(id);
        return "redirect:/" + apiVersion + "/pet";
    }

}
