package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.request.create.CreateAddressRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdateAddressRequest;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.service.AddressService;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
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
@RequestMapping("/${api.version}/address")
public class AddressController {

    @Value("${api.version}")
    private String apiVersion;
    private final AddressService addressService;
    private final PetOwnerService petOwnerService;

    public AddressController(AddressService addressService, PetOwnerService petOwnerService) {
        this.addressService = addressService;
        this.petOwnerService = petOwnerService;
    }

    @GetMapping
    public String getAddressList(Model model) {
        model.addAttribute("addresses", addressService.getAddressDtoList());
        return "addresses";
    }

    @GetMapping("/search")
    public String filterAddresses(Model model, @RequestParam String query) {
        model.addAttribute("addresses", addressService.doFilter(query));
        return "addresses";
    }

    @GetMapping("/create-address")
    public String getCreateAddressForm(Model model) {
        model.addAttribute("address", new CreateAddressRequest());
        addCurrentUserIdToModel(model);
        return "create-address";
    }

    @PostMapping("/create-address/{ownerId}")
    public String createAddress(@PathVariable String ownerId,
                                @ModelAttribute("address") @Valid CreateAddressRequest createAddressRequest,
                                BindingResult result,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("address", createAddressRequest);
            addCurrentUserIdToModel(model);
            return "create-address";
        }
        addressService.createAddress(createAddressRequest, ownerId);

        return "redirect:/" + apiVersion + "/address";
    }

    @GetMapping("/update-address/{id}")
    public String getUpdateAddressForm(Model model, @PathVariable String id) {
        model.addAttribute("addressId", id);
        model.addAttribute("address", addressService.getAddressById(id));
        return "update-address";
    }

    @PutMapping("update-address/{id}")
    public String updateAddress(@PathVariable String id,
                                @Valid @ModelAttribute("address") UpdateAddressRequest updateAddressRequest,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("addressId", id);
            model.addAttribute("address", updateAddressRequest);
            return "update-address";
        }

        addressService.updateAddress(id, updateAddressRequest);
        return "redirect:/" + apiVersion + "/address";
    }

    @GetMapping("delete-address/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteAddress(@PathVariable String id) {
        addressService.deleteAddressById(id);
        return "redirect:/" + apiVersion + "/address";
    }

    @GetMapping("add-address/{id}")
    public String addAddress(@PathVariable String id) {
        String ownerId = getCurrentUserId();
        addressService.addAddress(ownerId, id);
        return "redirect:/" + apiVersion + "/address";
    }

    @GetMapping("remove-address/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeAddress(@PathVariable String id) {

        String ownerId = getCurrentUserId();
        addressService.removeAddress(id, ownerId);
        return "redirect:/" + apiVersion + "/address";
    }

    private String getCurrentUserId() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return petOwnerService.findByEmail(currentUser.getUsername()).orElse(new PetOwner()).getId();
    }

    private void addCurrentUserIdToModel(Model model) {
        String id = getCurrentUserId();
        model.addAttribute("ownerId", id);
    }

}