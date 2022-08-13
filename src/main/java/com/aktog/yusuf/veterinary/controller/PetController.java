package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.dto.request.create.CreatePetRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetRequest;
import com.aktog.yusuf.veterinary.service.PetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/${api.version}/pet")
public class PetController {

    @Value("${api.version}")
    private String apiVersion;

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public String getPetList(Model model) {
        model.addAttribute("pets", petService.getPetDtoList());
        return "pets";
    }

    @GetMapping("/create-pet")
    public String getCreatePetForm(Model model){
        model.addAttribute("pet",new CreatePetRequest());
        return "create-pet";
    }

    @PostMapping("/create-pet")
    public String createPet(@ModelAttribute("createPetRequest") CreatePetRequest createPetRequest){
        petService.createPet("86911b7d-2a26-46e4-bc28-4342b6cd8cbd",createPetRequest);
        return "redirect:/" + apiVersion + "/pet";
    }

    @GetMapping("update-pet/{id}")
    public String getUpdatePetForm(@PathVariable String id, Model model){
        model.addAttribute("pet",petService.getPetById(id));
        return "update-pet";
    }

    @PostMapping("/update-pet/{id}")
    public String updatePet(@PathVariable String id,
                            @ModelAttribute("updatePetRequest")UpdatePetRequest updatePetRequest){
        petService.updatePet(id,updatePetRequest);
        return "redirect:/" + apiVersion + "/pet";

    }

    @GetMapping("/delete-pet/{id}")
    public String deletePet(@PathVariable String id){
        petService.deletePetById(id);
        return "redirect:/" + apiVersion + "/pet";
    }

}
