package com.aktog.yusuf.veterinary.dto.converter;

import com.aktog.yusuf.veterinary.dto.PetDto;
import com.aktog.yusuf.veterinary.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PetDtoConverter {

    public PetDto convert(Pet from) {
        return new PetDto(
                from.getId(),
                from.getName(),
                LocalDate.now().getYear() - from.getBirthDate().getYear(),
                from.getType(),
                from.getGenus(),
                from.getDescription(),
                from.getOwner().getName().concat(" " + from.getOwner().getSurname()),
                Optional.ofNullable(from.getOwner().getId()).orElse("")
        );
    }

    public List<PetDto> convert(List<Pet> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Page<PetDto> convert(Page<Pet> from) {
        return from.map(this::convert);
    }
}
