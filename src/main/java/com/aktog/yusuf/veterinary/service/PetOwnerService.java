package com.aktog.yusuf.veterinary.service;

import com.aktog.yusuf.veterinary.dto.PetOwnerDto;
import com.aktog.yusuf.veterinary.dto.converter.PetOwnerDtoConverter;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.exception.EmailAlreadyExistsException;
import com.aktog.yusuf.veterinary.exception.PhoneNumberAlreadyExistsException;
import com.aktog.yusuf.veterinary.repository.AuthorityRepository;
import com.aktog.yusuf.veterinary.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetOwnerService {

    private final PetOwnerDtoConverter petOwnerDtoConverter;
    private final PetOwnerRepository petOwnerRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public PetOwnerService(PetOwnerDtoConverter petOwnerDtoConverter,
                           PetOwnerRepository petOwnerRepository,
                           AuthorityRepository authorityRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.petOwnerDtoConverter = petOwnerDtoConverter;
        this.petOwnerRepository = petOwnerRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PetOwner findByPetOwnerId(String petOwnerId) {
        return petOwnerRepository.findById(petOwnerId)
                .orElseThrow(() -> new EntityNotFoundException("Pet Owner id : " + petOwnerId + " could not found"));
    }

    public PetOwnerDto getPetOwnerById(String petOwnerId) {
        return petOwnerDtoConverter.convert(findByPetOwnerId(petOwnerId));
    }

    // search by name or both with name and surname
    public List<PetOwnerDto> filterByName(String name) {
        return getPetOwnerDtoList()
                .stream()
                .filter(petOwnerDto -> petOwnerDto
                        .getName()
                        .concat(" " + petOwnerDto.getSurname())
                        .toUpperCase()
                        .contains(name.toUpperCase()))
                .collect(Collectors.toList());
    }

    public Optional<PetOwner> findByEmail(String email) {
        return petOwnerRepository.findByEmail(email);
    }

    public PetOwnerDto createPetOwner(CreatePetOwnerRequest request) {

        if (petOwnerRepository.findByEmail(request.getEmail()).isPresent())
            throw new EmailAlreadyExistsException("Given Email is already being used by another user");

        if (petOwnerRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent())
            throw new PhoneNumberAlreadyExistsException("Given Phone Number is already being used by another user");

        //By default, add normal user role to every owner created
        Authority user = new Authority("ROLE_USER");
        authorityRepository.save(user);

        PetOwner petOwner = new PetOwner(
                request.getName(),
                request.getSurname(),
                request.getPhoneNumber(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Set.of(authorityRepository.save(user))
        );

        return petOwnerDtoConverter.convert(petOwnerRepository.save(petOwner));
    }

    public PetOwnerDto updatePetOwner(String petOwnerId, UpdatePetOwnerRequest request) {
        PetOwner petOwner = findByPetOwnerId(petOwnerId);

        PetOwner updatedPetOwner = new PetOwner(
                petOwnerId,
                request.getName(),
                request.getSurname(),
                request.getPhoneNumber(),
                request.getEmail(),
                Optional.ofNullable(request.getPassword()).orElse(petOwner.getPassword()),
                Optional.ofNullable(request.getAuthorities()).orElse(petOwner.getAuthorities()),
                Optional.ofNullable(request.getPets()).orElse(petOwner.getPets()),
                Optional.ofNullable(request.getAddresses()).orElse(petOwner.getAddresses())
        );
        return petOwnerDtoConverter.convert(petOwnerRepository.save(updatedPetOwner));
    }

    public String deletePetOwner(String id) {
        findByPetOwnerId(id);
        petOwnerRepository.deleteById(id);
        return "Pet Owner with id: " + id + " has been deleted";
    }

    public List<PetOwnerDto> getPetOwnerDtoList() {
        return petOwnerDtoConverter.convert(getPetOwnerList());
    }

    public List<PetOwner> getPetOwnerList() {
        return petOwnerRepository.findAll();
    }

}
