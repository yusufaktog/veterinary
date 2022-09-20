package com.aktog.yusuf.veterinary.dto.converter;


import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.dto.PetOwnerDto;
import com.aktog.yusuf.veterinary.entity.Address;
import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.entity.Pet;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PetOwnerDtoConverter {

    public PetOwnerDto convert(PetOwner from) {
        return new PetOwnerDto(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getPhoneNumber(),
                from.getEmail(),

                Optional.ofNullable(from.getPets())
                        .orElse(new HashSet<>())
                        .stream()
                        .map(Pet::getId)
                        .collect(Collectors.toSet()),

                Optional.ofNullable(from.getAddresses())
                        .orElse(new HashSet<>())
                        .stream()
                        .map(Address::toString)
                        .collect(Collectors.toSet()),

                from.getAuthorities()
                        .stream()
                        .map(Authority::getName)
                        .collect(Collectors.toSet())

        );
    }

    public List<PetOwnerDto> convert(List<PetOwner> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Page<PetOwnerDto> convert(Page<PetOwner> from) {
        return from.map(this::convert);
    }

}
