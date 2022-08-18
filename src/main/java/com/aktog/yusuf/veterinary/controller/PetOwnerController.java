package com.aktog.yusuf.veterinary.controller;

import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.OnError;
import java.util.Collection;

@Controller
@RequestMapping("/${api.version}/owner")
public class PetOwnerController {
    @Value("${api.version}")
    private String apiVersion;
    private final PetOwnerService petOwnerService;

    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping("/search")
    public String filterPetOwners(Model model, @RequestParam String query) {
        model.addAttribute("owners", petOwnerService.doFilter(query));
        return "owners";
    }

    @GetMapping
    public String getPetOwnerList(Model model) {
        model.addAttribute("owners", petOwnerService.getPetOwnerDtoList());
        return "owners";
    }

    @GetMapping("/update-owner/{id}")
    public String getUpdateOwnerForm(@PathVariable String id, Model model) {
        model.addAttribute("owner", petOwnerService.getPetOwnerById(id));
        return "update-owner";
    }

    @PutMapping("/update-owner/{id}")
    public String updateOwner(@PathVariable String id,
                              @ModelAttribute @Valid UpdatePetOwnerRequest updatePetOwnerRequest) {
        petOwnerService.updatePetOwner(id, updatePetOwnerRequest);
        return "redirect:/" + apiVersion + "/owner";
    }

    @GetMapping("/delete-owner/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteOwner(@PathVariable String id) {
        petOwnerService.deletePetOwner(id);
        return "redirect:/" + apiVersion + "/owner";
    }

    @GetMapping("/assign-authority/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAssignAuthorityPage(@PathVariable String ownerId, Model model) {
        model.addAttribute("authority", new Authority());
        model.addAttribute("ownerId", ownerId);
        return "assign-authority";
    }

    @PostMapping("/assign-authority/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String assignAuthority(@PathVariable String ownerId, @ModelAttribute @Valid Authority authority) {
        petOwnerService.assignAuthorityToOwner(ownerId, authority);
        return "redirect:/" + apiVersion + "/owner";

    }


    @GetMapping("/remove-authority/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getRemoveAuthorityPage(@PathVariable String ownerId, Model model) {
        model.addAttribute("authority", new Authority());
        model.addAttribute("ownerId", ownerId);
        return "assign-authority";
    }

    @PostMapping("/remove-authority/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeAuthority(@PathVariable String ownerId, @ModelAttribute @Valid Authority authority) {
        petOwnerService.removeAuthorityFromOwner(ownerId, authority);
        return "redirect:/" + apiVersion + "/owner";
    }
}
