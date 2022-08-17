package com.aktog.yusuf.veterinary.service;

import com.aktog.yusuf.veterinary.TestDataGenerator;
import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.dto.converter.PetDtoConverter;
import com.aktog.yusuf.veterinary.entity.Pet;
import com.aktog.yusuf.veterinary.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PetServiceTest extends TestDataGenerator {
    private PetDtoConverter petDtoConverter;
    private PetRepository petRepository;
    private PetOwnerService petOwnerService;

    private PetService petService;

    @BeforeEach
    void setup() {
        petDtoConverter = Mockito.mock(PetDtoConverter.class);
        petRepository = Mockito.mock(PetRepository.class);
        petOwnerService = Mockito.mock(PetOwnerService.class);

        petService = new PetService(petDtoConverter, petRepository, petOwnerService);
    }

    @Test
    void testGetPetById_itShouldReturnPetDto() {
        Pet pet = generatePet(PET_ID);
        PetDto expected = generatePetDto(PET_ID);

        Mockito.when(petRepository.findById(PET_ID)).thenReturn(Optional.of(pet));
        Mockito.when(petDtoConverter.convert(pet)).thenReturn(expected);

        PetDto actual = petService.getPetById(PET_ID);

        assertEquals(expected, actual);

        Mockito.verify(petRepository).findById(PET_ID);
        Mockito.verify(petDtoConverter).convert(pet);

    }

    @Test
    void testDeletePetById_whenPetIdExists_itShouldReturnString() {
        Pet pet = generatePet(PET_ID);
        Mockito.when(petRepository.findById(PET_ID)).thenReturn(Optional.of(pet));

        String actual = petService.deletePetById(PET_ID);
        String expected = "Pet id  : " + PET_ID + " deleted";

        assertEquals(expected, actual);

        Mockito.verify(petRepository).findById(PET_ID);
    }

    @Test
    void testDeletePetById_whenPetIdNotExist_itShouldThrowEntityNotFoundException(){
        Mockito.when(petRepository.findById(PET_ID)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> petService.findByPetId(PET_ID));

        Mockito.verify(petRepository).findById(PET_ID);
        Mockito.verifyNoInteractions(petDtoConverter);
    }
    @Test
    void testGetAllPetDtoList_itShouldReturnPetDtoList() {
        List<Pet> petList = generatePetList(PET_ID);
        List<PetDto> expected = generatePetDtoList(PET_ID);

        Mockito.when(petRepository.findAll()).thenReturn(petList);
        Mockito.when(petDtoConverter.convert(petList)).thenReturn(expected);

        List<PetDto> actual = petService.getPetDtoList();

        assertEquals(expected, actual);

        Mockito.verify(petRepository).findAll();
        Mockito.verify(petDtoConverter).convert(petList);
    }

    @Test
    void testGetAllPetList_itShouldReturnPetList() {
        List<Pet> actual = generatePetList(PET_ID);

        Mockito.when(petRepository.findAll()).thenReturn(actual);

        List<Pet> expected = petService.getPetList();

        assertEquals(expected, actual);

        Mockito.verify(petRepository).findAll();
    }
}
