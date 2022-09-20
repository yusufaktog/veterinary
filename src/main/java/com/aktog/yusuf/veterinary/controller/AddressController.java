package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.AddressDto;
import com.aktog.yusuf.veterinary.dto.request.create.CreateAddressRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdateAddressRequest;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.service.AddressService;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
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
    public String findPaginated(Model model,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "q", defaultValue = "", required = false) String query) {
        model.addAttribute("page", addressService.findPaginated(pageNo, query));
        model.addAttribute("query", query);

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

        return String.format("redirect:/%s/address", apiVersion);

    }

    @GetMapping("/update-address/{id}")
    public String getUpdateAddressForm(Model model, @PathVariable String id) {
        model.addAttribute("addressId", id);
        model.addAttribute("address", addressService.getAddressById(id));
        return "update-address";
    }

    @GetMapping("add-address/{id}")
    public String addAddress(@PathVariable String id) {
        String ownerId = getCurrentUserId();
        addressService.addAddress(ownerId, id);

        return String.format("redirect:/%s/address", apiVersion);
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

        return String.format("redirect:/%s/address", apiVersion);
    }

    @GetMapping("delete-address/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteAddress(@PathVariable String id,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "q", defaultValue = "", required = false) String query) {

        addressService.deleteAddressById(id);

        return String.format("redirect:/%s/address?p=%d&q=%s", apiVersion, pageNo + 1, query);
    }

    @GetMapping("remove-address/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeAddress(@PathVariable String id,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "q", defaultValue = "", required = false) String query) {

        String ownerId = getCurrentUserId();
        addressService.removeAddress(id, ownerId);

        return String.format("redirect:/%s/address?p=%d&q=%s", apiVersion, pageNo + 1, query);
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