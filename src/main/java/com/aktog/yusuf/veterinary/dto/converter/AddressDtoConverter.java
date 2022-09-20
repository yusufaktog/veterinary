package com.aktog.yusuf.veterinary.dto.converter;

import com.aktog.yusuf.veterinary.dto.AddressDto;
import com.aktog.yusuf.veterinary.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressDtoConverter {


    public AddressDto convert(Address from) {
        return new AddressDto(
                from.getId(),
                from.getCountry(),
                from.getCity(),
                from.getStreet(),
                from.getBuildingNumber(),
                from.getApartmentNumber(),
                from.getZipCode()
        );
    }

    public List<AddressDto> convert(List<Address> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    public Page<AddressDto> convert(Page<Address> from){
        return from.map(this::convert);
    }

}
