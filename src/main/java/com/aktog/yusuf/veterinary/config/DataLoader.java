package com.aktog.yusuf.veterinary.config;

import com.aktog.yusuf.veterinary.entity.Address;
import com.aktog.yusuf.veterinary.repository.AddressRepository;
import com.aktog.yusuf.veterinary.repository.AuthorityRepository;
import com.aktog.yusuf.veterinary.repository.PetOwnerRepository;
import com.aktog.yusuf.veterinary.repository.PetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final AddressRepository addressRepository;
    private final PetRepository petRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final AuthorityRepository authorityRepository;

    public DataLoader(AddressRepository addressRepository,
                      PetRepository petRepository,
                      PetOwnerRepository petOwnerRepository,
                      AuthorityRepository authorityRepository) {
        this.addressRepository = addressRepository;
        this.petRepository = petRepository;
        this.petOwnerRepository = petOwnerRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) {
//        System.out.println(petOwnerRepository.findByEmail("yusufaktok@gmail.com").isPresent());

/*        Address address = new Address(
                "Turkey",
                "Izmir",
                "Kadriye",
                150,
                15,
                35270
        );
        addressRepository.save(address);
        address = new Address(
                "Turkey",
                "Istanbul",
                "Sariyer",
                350,
                35,
                55670
        );
        addressRepository.save(address);*/

    }
}
