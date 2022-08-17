package com.aktog.yusuf.veterinary.config;

import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final PetOwnerService petOwnerService;

    public DataLoader(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }


    @Override
    public void run(String... args) {
        // By Default an admin account will be automatically generated,
        // This account can be used to assign authorities to other users.
        String email = "admin@gmail.com";

        if (petOwnerService.findByEmail(email).isEmpty()) {
            System.out.println("did not find any admin accounts so createing a new one");
            CreatePetOwnerRequest admin = new CreatePetOwnerRequest("ADMIN",
                    "ADMIN",
                    "0000000000",
                    email,
                    "admin123",
                    Set.of(new Authority("ROLE_ADMIN")));
            petOwnerService.createPetOwner(admin);
        }


    }
}
