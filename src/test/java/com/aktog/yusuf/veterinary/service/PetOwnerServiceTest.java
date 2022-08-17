package com.aktog.yusuf.veterinary.service;

import com.aktog.yusuf.veterinary.TestDataGenerator;
import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.dto.PetOwnerDto;
import com.aktog.yusuf.veterinary.dto.converter.PetOwnerDtoConverter;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.entity.Pet;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.exception.EmailAlreadyExistsException;
import com.aktog.yusuf.veterinary.exception.PhoneNumberAlreadyExistsException;
import com.aktog.yusuf.veterinary.repository.AuthorityRepository;
import com.aktog.yusuf.veterinary.repository.PetOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PetOwnerServiceTest extends TestDataGenerator {

    private PetOwnerDtoConverter petOwnerDtoConverter;
    private PetOwnerRepository petOwnerRepository;
    private AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder passwordEncoder;

    private PetOwnerService petOwnerService;

    @BeforeEach
    void setUp() {
        petOwnerDtoConverter = Mockito.mock(PetOwnerDtoConverter.class);
        petOwnerRepository = Mockito.mock(PetOwnerRepository.class);
        authorityRepository = Mockito.mock(AuthorityRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        petOwnerService = new PetOwnerService(petOwnerDtoConverter,
                petOwnerRepository,
                authorityRepository,
                passwordEncoder);

    }

    @Test
    void createPetOwner_whenEmailConflicts_itShouldReturnEmailAlreadyExistsException() {
        CreatePetOwnerRequest request = generateCreatePetOwnerRequest();

        Mockito.when(petOwnerService.findByEmail("email")).thenThrow(EmailAlreadyExistsException.class);

        assertThrows(EmailAlreadyExistsException.class, () -> petOwnerService.createPetOwner(request));

        Mockito.verify(petOwnerRepository).findByEmail("email");
        Mockito.verifyNoInteractions(authorityRepository);
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoInteractions(petOwnerDtoConverter);

    }

    @Test
    void createPetOwner_whenPhoneNumberConflicts_itShouldReturnPhoneNumberAlreadyExistsException() {
        CreatePetOwnerRequest request = generateCreatePetOwnerRequest();

        Mockito.when(petOwnerRepository.findByPhoneNumber("phoneNumber")).thenThrow(PhoneNumberAlreadyExistsException.class);

        assertThrows(PhoneNumberAlreadyExistsException.class, () -> petOwnerService.createPetOwner(request));

        Mockito.verify(petOwnerRepository).findByPhoneNumber("phoneNumber");
        Mockito.verifyNoInteractions(authorityRepository);
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoInteractions(petOwnerDtoConverter);
    }

    @Test
    void createPetOwner_itShouldReturnPetOwnerDto() {
        CreatePetOwnerRequest request = generateCreatePetOwnerRequest();
        PetOwner petOwner = generatePetOwner();
        Authority authority = generateAuthority(ROLE_ID);

        PetOwnerDto expected = generatePetOwnerDto(OWNER_ID);

        Mockito.when(petOwnerRepository.findByEmail("email")).thenReturn(Optional.empty());
        Mockito.when(petOwnerRepository.findByPhoneNumber("phoneNumber")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("password");
        Mockito.when(authorityRepository.findByName("ROLE_USER")).thenReturn(Optional.ofNullable(authority));
        Mockito.when(petOwnerRepository.save(petOwner)).thenReturn(petOwner);
        Mockito.when(petOwnerDtoConverter.convert(petOwner)).thenReturn(expected);

        PetOwnerDto actual = petOwnerService.createPetOwner(request);

        assertEquals(expected, actual);

        Mockito.verify(petOwnerRepository).findByEmail("email");
        Mockito.verify(petOwnerRepository).findByPhoneNumber("phoneNumber");
        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verify(authorityRepository).findByName("ROLE_USER");
        Mockito.verify(petOwnerRepository).save(petOwner);
        Mockito.verify(petOwnerDtoConverter).convert(petOwner);
    }

    @Test
    void testUpdatePetOwner_whenOwnerIdNotExist() {

        Mockito.when(petOwnerRepository.findById(OWNER_ID)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> petOwnerService.updatePetOwner(OWNER_ID, generateUpdatePetOwnerRequest()));

        Mockito.verify(petOwnerRepository).findById(OWNER_ID);
        Mockito.verifyNoInteractions(petOwnerDtoConverter);

    }

    @Test
    void testUpdatePetOwner_whenIdExist_itShouldReturnPetOwnerDto() {
        UpdatePetOwnerRequest request = generateUpdatePetOwnerRequest();
        PetOwner updatedOwner = generateUpdatedPetOwner(generatePetOwner(OWNER_ID), request);
        PetOwnerDto expected = generatePetOwnerDto(OWNER_ID);

        Mockito.when(petOwnerRepository.findById(OWNER_ID)).thenReturn(Optional.of(generatePetOwner(OWNER_ID)));
        Mockito.when(petOwnerRepository.save(updatedOwner)).thenReturn(updatedOwner);
        Mockito.when(petOwnerDtoConverter.convert(updatedOwner)).thenReturn(expected);

        PetOwnerDto actual = petOwnerService.updatePetOwner(OWNER_ID, request);

        assertEquals(expected, actual);

        Mockito.verify(petOwnerRepository).findById(OWNER_ID);
        Mockito.verify(petOwnerRepository).save(updatedOwner);
        Mockito.verify(petOwnerDtoConverter).convert(updatedOwner);
    }

    @Test
    void testDeletePetOwner_whenPetOwnerIdNotExist_itShouldThrowEntityNotFoundException() {

        Mockito.when(petOwnerRepository.findById(OWNER_ID)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> petOwnerService.deletePetOwner(OWNER_ID));

        Mockito.verify(petOwnerRepository).findById(OWNER_ID);
        Mockito.verifyNoInteractions(petOwnerDtoConverter);
    }

    @Test
    void testGetAllPetOwnerDtoList_itShouldReturnPetOwnerDtoList() {
        List<PetOwner> petOwnerList = generatePetOwnerList(PET_ID);
        List<PetOwnerDto> expected = generatePetOwnerDtoList(PET_ID);

        Mockito.when(petOwnerRepository.findAll()).thenReturn(petOwnerList);
        Mockito.when(petOwnerDtoConverter.convert(petOwnerList)).thenReturn(expected);

        List<PetOwnerDto> actual = petOwnerService.getPetOwnerDtoList();

        assertEquals(expected, actual);

        Mockito.verify(petOwnerRepository).findAll();
        Mockito.verify(petOwnerDtoConverter).convert(petOwnerList);
    }

    @Test
    void testGetAllPetOwnerList_itShouldReturnPetOwnerList() {
        List<PetOwner> actual = generatePetOwnerList(PET_ID);

        Mockito.when(petOwnerRepository.findAll()).thenReturn(actual);

        List<PetOwner> expected = petOwnerService.getPetOwnerList();

        assertEquals(expected, actual);

        Mockito.verify(petOwnerRepository).findAll();
    }

    @Test
    void testGetPetOwnerById_itShouldReturnPetOwnerDto() {
        PetOwner pet = generatePetOwner(PET_ID);
        PetOwnerDto expected = generatePetOwnerDto(PET_ID);

        Mockito.when(petOwnerRepository.findById(PET_ID)).thenReturn(Optional.of(pet));
        Mockito.when(petOwnerDtoConverter.convert(pet)).thenReturn(expected);

        PetOwnerDto actual = petOwnerService.getPetOwnerById(PET_ID);

        assertEquals(expected, actual);

        Mockito.verify(petOwnerRepository).findById(PET_ID);
        Mockito.verify(petOwnerDtoConverter).convert(pet);

    }

    @Test
    void testFindByPhoneNumber_itShouldReturnPetOwner() {

        PetOwner expected = generatePetOwner(OWNER_ID);

        Mockito.when(petOwnerRepository.findByPhoneNumber("phoneNumber")).thenReturn(Optional.of(expected));

        Optional<PetOwner> actual = petOwnerService.findByPhoneNumber("phoneNumber");
        assertEquals(expected, actual.isPresent() ? actual.get() : Optional.empty());

        Mockito.verify(petOwnerRepository).findByPhoneNumber("phoneNumber");
    }

    @Test
    void testFindByEmail_itShouldReturnPetOwner() {

        PetOwner expected = generatePetOwner(OWNER_ID);

        Mockito.when(petOwnerRepository.findByEmail("email")).thenReturn(Optional.of(expected));

        Optional<PetOwner> actual = petOwnerService.findByEmail("email");
        assertEquals(expected, actual.isPresent() ? actual.get() : Optional.empty());

        Mockito.verify(petOwnerRepository).findByEmail("email");
    }

    @Test
    void testDoFilter_itShouldReturnListOfFilteredPetOwners() {

        PetOwnerDto yusuf = new PetOwnerDto(
                OWNER_ID,
                "yusuf",
                "surname",
                "phoneNumber",
                "email");

        PetOwnerDto ahmet = new PetOwnerDto(
                OWNER_ID,
                "ahmet",
                "surname",
                "phoneNumber",
                "email");

        List<PetOwnerDto> expected = List.of(yusuf);

        List<PetOwner> testData = generateTestDataForDoFilter();

        Mockito.when(petOwnerRepository.findAll()).thenReturn(testData);
        Mockito.when(petOwnerDtoConverter.convert(testData)).thenReturn(List.of(yusuf, ahmet));

        List<PetOwnerDto> actual = petOwnerService.doFilter("yusuf");

        assertEquals(expected, actual);

        Mockito.verify(petOwnerRepository, Mockito.atLeastOnce()).findAll();
        Mockito.verify(petOwnerDtoConverter).convert(testData);

    }

}