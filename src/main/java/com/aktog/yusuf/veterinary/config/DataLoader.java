package com.aktog.yusuf.veterinary.config;

import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.repository.AuthorityRepository;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final PetOwnerService petOwnerService;
    private final AuthorityRepository authorityRepository;

    public DataLoader(PetOwnerService petOwnerService, AuthorityRepository authorityRepository) {
        this.petOwnerService = petOwnerService;
        this.authorityRepository = authorityRepository;
    }


    @Override
    public void run(String... args) {
        // By Default an admin account will be automatically generated,
        // This account can be used to assign authorities to other users.
        String email = "admin@gmail.com";

        if (petOwnerService.findByEmail(email).isEmpty()) {
            Authority authority = authorityRepository.save(new Authority("ROLE_ADMIN"));
            CreatePetOwnerRequest admin = new CreatePetOwnerRequest("ADMIN",
                    "ADMIN",
                    "0000000000",
                    email,
                    "admin123",
                    Set.of(authority));
            petOwnerService.createPetOwner(admin);
        }



    }
}
