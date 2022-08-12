package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.request.create.CreateAddressRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdateAddressRequest;
import com.aktog.yusuf.veterinary.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/${api.version}/address")
public class AddressController {

    @Value("${api.version}")
    private String apiVersion;
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping
    public String getAddressList(Model model) {
        model.addAttribute("addresses", addressService.getAddressDtoList());
        return "addresses";
    }

    @GetMapping("/create-address")
    public String getCreateAddressForm(Model model) {
        model.addAttribute("address", new CreateAddressRequest());
        return "create-address";
    }

    @PostMapping("/create-address")
    public String createAddress(@ModelAttribute("createAddressRequest") CreateAddressRequest createAddressRequest) {

        addressService.createAddress(createAddressRequest);
        return "redirect:/" + apiVersion + "/address";
    }

    @GetMapping("/update-address/{id}")
    public String getUpdateAddressForm(Model model, @PathVariable String id) {
        model.addAttribute("address", addressService.getAddressById(id));
        return "update-address";
    }

    @PostMapping("update-address/{id}")
    public String updateAddress(Model model,
                                @PathVariable String id,
                                @ModelAttribute("updateAddressRequest") UpdateAddressRequest updateAddressRequest) {
        addressService.updateAddress(id, updateAddressRequest);
        return "redirect:/" + apiVersion + "/address";
    }

    @GetMapping("delete-address/{id}")
    public String deleteAddress(@PathVariable String id) {
        addressService.deleteAddressById(id);
        return "redirect:/" + apiVersion + "/address";
    }

}
