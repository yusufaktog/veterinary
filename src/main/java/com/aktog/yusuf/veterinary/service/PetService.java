package com.aktog.yusuf.veterinary.service;


import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.dto.PetOwnerDto;
import com.aktog.yusuf.veterinary.dto.converter.PetDtoConverter;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetRequest;
import com.aktog.yusuf.veterinary.entity.Pet;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.SchemaOutputResolver;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetDtoConverter petDtoConverter;
    private final PetRepository petRepository;

    private final PetOwnerService petOwnerService;

    public PetService(PetDtoConverter petDtoConverter,
                      PetRepository petRepository,
                      PetOwnerService petOwnerService) {
        this.petDtoConverter = petDtoConverter;
        this.petRepository = petRepository;
        this.petOwnerService = petOwnerService;
    }

    public Pet findByPetId(String petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet id : " + petId + " could not found"));
    }

    public PetDto getPetById(String petId) {
        return petDtoConverter.convert(findByPetId(petId));
    }

    public String deletePetById(String petId) {
        findByPetId(petId);
        petRepository.deleteById(petId);
        return "Address id  : " + petId + " deleted";
    }

    public PetDto createPet(String ownerId, CreatePetRequest request) {
        PetOwner owner = petOwnerService.findByPetOwnerId(ownerId);
        Pet pet = new Pet(
                request.getName(),
                request.getBirthDate(),
                request.getType(),
                request.getGenus(),
                request.getDescription(),
                owner
        );
        return petDtoConverter.convert(petRepository.save(pet));
    }

    public PetDto updatePet(String petId, UpdatePetRequest request) {
        Pet pet = findByPetId(petId);
        String ownerId = Optional.ofNullable(request.getOwnerId()).orElse(pet.getOwner().getId());
        Pet updatedPet = new Pet(
                petId,
                Optional.ofNullable(request.getName()).orElse(pet.getName()),
                pet.getBirthDate(),
                pet.getType(),
                pet.getGenus(),
                Optional.ofNullable(request.getDescription()).orElse(pet.getDescription()),
                petOwnerService.findByPetOwnerId(ownerId)
        );
        return petDtoConverter.convert(petRepository.save(updatedPet));
    }

    public List<Pet> getPetList() {
        return petRepository.findAll();
    }

    public List<PetDto> getPetDtoList() {
        return petDtoConverter.convert(getPetList());
    }

    public List<PetDto> doFilter(String query) {
        return getPetDtoList()
                .stream()
                .filter(petDto -> petDto
                        .toString()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());

    }
}
