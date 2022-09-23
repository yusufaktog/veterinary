package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetRequest;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import com.aktog.yusuf.veterinary.service.PetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
    public String findPaginated(Model model,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "q", defaultValue = "") String query,
                                @RequestParam(name = "f", defaultValue = "name", required = false) String sortField,
                                @RequestParam(name = "t", defaultValue = "3", required = false) Integer sortType) {
        model.addAttribute("page", petService.findPaginated(pageNo, query, sortField, sortType));
        model.addAttribute("query", query);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);

        return "pets";
    }

    @GetMapping("/create-pet")
    public String getCreatePetForm(Model model) {
        addCurrentUserIdToModel(model);
        model.addAttribute("pet", new CreatePetRequest());

        return "create-pet";
    }

    @PostMapping("/create-pet/{ownerId}")
    public String createPet(@PathVariable String ownerId,
                            @Valid @ModelAttribute("pet") CreatePetRequest createPetRequest,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            addCurrentUserIdToModel(model);
            model.addAttribute("pet", createPetRequest);
            return "create-pet";
        }
        petService.createPet(ownerId, createPetRequest);

        return "redirect:/" + apiVersion + "/pet";
    }

    @GetMapping("update-pet/{id}")
    public String getUpdatePetForm(@PathVariable String id, Model model) {
        model.addAttribute("pet", petService.getPetById(id));
        model.addAttribute("petId", id);

        return "update-pet";
    }

    @PutMapping("/update-pet/{id}")
    public String updatePet(@PathVariable String id,
                            @ModelAttribute @Valid UpdatePetRequest updatePetRequest,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pet", updatePetRequest);
            model.addAttribute("petId", id);

            return "update-pet";
        }
        petService.updatePet(id, updatePetRequest);

        return String.format("redirect:/%s/pet", apiVersion);
    }

    @GetMapping("/delete-pet/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePet(@PathVariable String id,
                            @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "q", defaultValue = "", required = false) String query) {
        petService.deletePetById(id);

        return String.format("redirect:/%s/pet?p=%d&q=%s", apiVersion, pageNo + 1, query);
    }

    private void addCurrentUserIdToModel(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = petOwnerService.findByEmail(currentUser.getUsername()).orElse(new PetOwner()).getId();
        model.addAttribute("ownerId", id);
    }

}