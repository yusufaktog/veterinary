package com.aktog.yusuf.veterinary.controller;

import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String findPaginated(Model model,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "q", defaultValue = "") String query) {
        model.addAttribute("page", petOwnerService.findPaginated(pageNo, query));
        model.addAttribute("query", query);

        return "owners";
    }


    @GetMapping("/update-owner/{id}")
    public String getUpdateOwnerForm(@PathVariable String id, Model model) {
        model.addAttribute("owner", petOwnerService.getPetOwnerById(id));
        model.addAttribute("ownerId", id);

        return "update-owner";
    }

    @PutMapping("/update-owner/{id}")
    public String updateOwner(@PathVariable String id,
                              @Valid @ModelAttribute("owner") UpdatePetOwnerRequest updatePetOwnerRequest,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("owner", updatePetOwnerRequest);
            model.addAttribute("ownerId", id);
            return "update-owner";
        }
        petOwnerService.updatePetOwner(id, updatePetOwnerRequest);

        return String.format("redirect:/%s/owner", apiVersion);
    }

    @GetMapping("/delete-owner/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteOwner(@PathVariable String id,
                              @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "q", defaultValue = "", required = false) String query) {
        petOwnerService.deletePetOwner(id);

        return String.format("redirect:/%s/owner?p=%d&q=%s", apiVersion, pageNo + 1, query);

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

        return String.format("redirect:/%s/owner", apiVersion);
    }

    @GetMapping("/clear-addresses/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String clearAddresses(@PathVariable String ownerId) {
        petOwnerService.clearAddresses(ownerId);

        return String.format("redirect:/%s/owner", apiVersion);
    }

    @GetMapping("/remove-authority/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getRemoveAuthorityPage(@PathVariable String ownerId, Model model) {
        model.addAttribute("authority", new Authority());
        model.addAttribute("ownerId", ownerId);

        return "remove-authority";
    }

    @PostMapping("/remove-authority/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeAuthority(@PathVariable String ownerId, @ModelAttribute @Valid Authority authority) {
        petOwnerService.removeAuthorityFromOwner(ownerId, authority);

        return String.format("redirect:/%s/owner", apiVersion);
    }
}