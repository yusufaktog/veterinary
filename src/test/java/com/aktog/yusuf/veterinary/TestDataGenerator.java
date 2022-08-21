package com.aktog.yusuf.veterinary;

import com.aktog.yusuf.veterinary.dto.AddressDto;
import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.dto.PetOwnerDto;
import com.aktog.yusuf.veterinary.dto.request.create.CreatePetOwnerRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Address;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.entity.Pet;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestDataGenerator {
    public static final LocalDate TEST_DATE = LocalDate.of(2020, 11, 11);

    public static final String OWNER_ID = "owner_id";
    public static final String PET_ID = "pet_id";
    public static final String ADDRESS_ID = "address_id";
    public static final String ROLE = "ROLE_USER";

    public Pet generatePet(@Nullable String id) {
        return new Pet(
                id,
                "name",
                TEST_DATE,
                "type",
                "genus",
                "description",
                generatePetOwner("owner_id")
        );
    }

    public PetDto generatePetDto(@Nullable String id) {
        return new PetDto(id,
                "name",
                LocalDate.now().getYear() - TEST_DATE.getYear(),
                "type",
                "genus",
                "description",
                "name",
                "owner_id");
    }

    public PetOwner generatePetOwner(String id) {
        return new PetOwner(
                id,
                "name",
                "surname",
                "phoneNumber",
                "email",
                "password",
                Set.of(generateAuthority()),
                Collections.emptySet(),
                Collections.emptySet()
        );
    }

    public PetOwner generatePetOwner() {
        return new PetOwner(
                "name",
                "surname",
                "phoneNumber",
                "email",
                "password",
                Set.of(generateAuthority())
        );
    }

    public PetOwnerDto generatePetOwnerDto(String id) {
        return new PetOwnerDto(
                id,
                "name",
                "surname",
                "phoneNumber",
                "email"
        );
    }
    public PetOwnerDto generatePetOwnerDto(Authority authority) {
        return new PetOwnerDto(
                OWNER_ID,
                "name",
                "surname",
                "phoneNumber",
                "email",
                Collections.emptySet(),
                Collections.emptySet(),
                Set.of(authority.getName())
        );
    }

    public Address generateAddress(String id) {
        return new Address(
                id,
                "country",
                "city",
                "street",
                1,
                1,
                1
        );
    }

    public AddressDto generateAddressDto(@Nullable String id) {
        return new AddressDto(
                id,
                "country",
                "city",
                "street",
                1,
                1,
                1
        );
    }

    public List<PetOwner> generateTestDataForDoFilter() {
        PetOwner yusuf = new PetOwner(
                OWNER_ID,
                "yusuf",
                "surname",
                "phoneNmber",
                "email",
                "password",
                Collections.emptySet());

        PetOwner ahmet = new PetOwner(OWNER_ID,
                "ahmet",
                "surname",
                "phoneNmber",
                "email",
                "password",
                Collections.emptySet());

        return List.of(yusuf,ahmet);

    }

    public CreatePetOwnerRequest generateCreatePetOwnerRequest() {
        return new CreatePetOwnerRequest(
                "name",
                "surname",
                "phoneNumber",
                "email",
                "password",
                Set.of(generateAuthority()));
    }

    public UpdatePetOwnerRequest generateUpdatePetOwnerRequest() {
        return new UpdatePetOwnerRequest(
                "name",
                "surname",
                "phoneNumber",
                "email",
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );
    }

    public PetOwner generateUpdatedPetOwner(PetOwner owner, UpdatePetOwnerRequest request) {
        return new PetOwner(
                owner.getId(),
                request.getName(),
                request.getSurname(),
                request.getPhoneNumber(),
                request.getEmail(),
                "password",
                Collections.emptySet(),
                request.getPets(),
                request.getAddresses()
        );
    }

    public Authority generateAuthority() {
        return new Authority(
                "ROLE_USER"
        );
    }

    public List<Pet> generatePetList(String id) {
        return List.of(generatePet(id));
    }

    public List<PetDto> generatePetDtoList(String id) {
        return List.of(generatePetDto(id));
    }

    public List<PetOwner> generatePetOwnerList(String id) {
        return List.of(generatePetOwner(id));
    }

    public List<PetOwnerDto> generatePetOwnerDtoList(String id) {
        return List.of(generatePetOwnerDto(id));
    }
}
