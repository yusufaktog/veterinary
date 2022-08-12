package com.aktog.yusuf.veterinary.service;

import com.aktog.yusuf.veterinary.dto.PetOwnerDto;
import com.aktog.yusuf.veterinary.dto.converter.PetOwnerDtoConverter;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.exception.EmailAlreadyExistsException;
import com.aktog.yusuf.veterinary.repository.PetOwnerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetOwnerService {

    private final PetOwnerDtoConverter petOwnerDtoConverter;
    private final PetOwnerRepository petOwnerRepository;


    public PetOwnerService(PetOwnerDtoConverter petOwnerDtoConverter, PetOwnerRepository petOwnerRepository) {
        this.petOwnerDtoConverter = petOwnerDtoConverter;
        this.petOwnerRepository = petOwnerRepository;
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

    public PetOwnerDto createPetOwner(CreatePetOwnerRequest request) {
        if(petOwnerRepository.findByEmail(request.getEmail()).isPresent())
            throw new EmailAlreadyExistsException("Given Email is already being used by another user");
        PetOwner petOwner = new PetOwner(
                request.getName(),
                request.getSurname(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword()
        );
        return petOwnerDtoConverter.convert(petOwnerRepository.save(petOwner));
    }

    public PetOwnerDto updatePetOwner(String petOwnerId, UpdatePetOwnerRequest request) {
        PetOwner petOwner = findByPetOwnerId(petOwnerId);
        if(petOwnerRepository.findByEmail(request.getEmail()).isPresent())
            throw new EmailAlreadyExistsException("Given Email is already being used by another user");
        PetOwner updatedPetOwner = new PetOwner(
                petOwnerId,
                request.getName(),
                request.getSurname(),
                request.getPhoneNumber(),
                request.getEmail(),
                Optional.ofNullable(request.getPassword()).orElse(petOwner.getPassword()),
                Optional.ofNullable(request.getPets()).orElse(petOwner.getPets()),
                Optional.ofNullable(request.getAddresses()).orElse(petOwner.getAddresses()),
                Optional.ofNullable(request.getAuthorities()).orElse(petOwner.getAuthorities())
        );
        return petOwnerDtoConverter.convert(petOwnerRepository.save(updatedPetOwner));
    }

    public String deletePetOwner(String id){
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
