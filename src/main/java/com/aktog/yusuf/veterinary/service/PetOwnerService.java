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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public Optional<PetOwner> findByEmail(String email) {
        return petOwnerRepository.findByEmail(email);
    }

    public Optional<PetOwner> findByPhoneNumber(String phoneNumber) {
        return petOwnerRepository.findByPhoneNumber(phoneNumber);
    }

    public PetOwnerDto createPetOwner(CreatePetOwnerRequest request) {

        if (petOwnerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Given Email is already being used by another user");
        }

        if (petOwnerRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Given Phone Number is already being used by another user");
        }

        //By default, add normal user role to every owner created
        Authority authority = authorityRepository.findByName("ROLE_USER")
                .orElse(authorityRepository.save(new Authority("ROLE_USER")));

        Set<Authority> authorities = new HashSet<>(Optional.ofNullable(request.getAuthorities()).orElse(Set.of(authority)));
        authorities.add(authority);

        PetOwner petOwner = new PetOwner(
                request.getName(),
                request.getSurname(),
                request.getPhoneNumber(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                authorities);

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
                petOwner.getPassword(),
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

    public List<PetOwnerDto> doFilter(String query) {
        return getPetOwnerDtoList()
                .stream()
                .filter(petOwnerDto -> petOwnerDto
                        .toString()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public PetOwnerDto assignAuthorityToOwner(String ownerId, Authority authority) {

        PetOwner owner = findByPetOwnerId(ownerId);
        Set<Authority> authorities = new HashSet<>(owner.getAuthorities());

        authorities.add(authorityRepository.save(authority));

        PetOwner updatedOwner = new PetOwner(
                ownerId,
                owner.getName(),
                owner.getSurname(),
                owner.getPhoneNumber(),
                owner.getEmail(),
                owner.getPassword(),
                authorities,
                owner.getPets(),
                owner.getAddresses()
        );

        return petOwnerDtoConverter.convert(petOwnerRepository.save(updatedOwner));

    }

    public void removeAuthorityFromOwner(String ownerId, Authority authority) {
        PetOwner owner = findByPetOwnerId(ownerId);
        authorityRepository.findByName(authority.getName())
                .orElseThrow(() -> new EntityNotFoundException("Authority : " + authority.getName() + " could not found"));
        Set<Authority> authorities = new HashSet<>(owner.getAuthorities());

        authorities.remove(authorityRepository.findByName(authority.getName()).orElse(authority));

        PetOwner updatedOwner = new PetOwner(
                ownerId,
                owner.getName(),
                owner.getSurname(),
                owner.getPhoneNumber(),
                owner.getEmail(),
                owner.getPassword(),
                authorities,
                owner.getPets(),
                owner.getAddresses()
        );
        petOwnerRepository.save(updatedOwner);
    }

}