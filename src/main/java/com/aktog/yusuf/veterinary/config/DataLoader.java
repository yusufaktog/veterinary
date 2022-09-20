package com.aktog.yusuf.veterinary.config;

import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.repository.AuthorityRepository;
import com.aktog.yusuf.veterinary.service.AddressService;
import com.aktog.yusuf.veterinary.service.PetOwnerService;
import com.aktog.yusuf.veterinary.service.PetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final PetOwnerService petOwnerService;
    private final PetService petService;

    private final AuthorityRepository authorityRepository;

    public DataLoader(PetOwnerService petOwnerService, PetService petService, AuthorityRepository authorityRepository) {
        this.petOwnerService = petOwnerService;
        this.petService = petService;
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

/*        for (int i = 1; i < 30; i++) {
            petService.createPet("e60ffa6e-a2c1-4d18-b9ff-4b36c899c3e7",new CreatePetRequest(
                    "Type " + i,
                    "Genus " + i,
                    "Pet " + i,
                    LocalDate.of(2000,1,1),
                    "Description " + i
            ));
        }*/
    }
}
