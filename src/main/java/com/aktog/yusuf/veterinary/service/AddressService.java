package com.aktog.yusuf.veterinary.service;


import com.aktog.yusuf.veterinary.dto.AddressDto;
import com.aktog.yusuf.veterinary.dto.converter.AddressDtoConverter;
import com.aktog.yusuf.veterinary.dto.request.create.CreateAddressRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdateAddressRequest;
import com.aktog.yusuf.veterinary.entity.Address;
import com.aktog.yusuf.veterinary.repository.AddressRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressDtoConverter addressDtoConverter;
    private final AddressRepository addressRepository;

    public AddressService(AddressDtoConverter addressDtoConverter, AddressRepository addressRepository) {
        this.addressDtoConverter = addressDtoConverter;
        this.addressRepository = addressRepository;
    }

    public Address findByAddressId(String addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address id : " + addressId + " could not found"));
    }

    public AddressDto getAddressById(String addressId) {
        return addressDtoConverter.convert(findByAddressId(addressId));
    }

    public String deleteAddressById(String addressId) {
        findByAddressId(addressId);
        addressRepository.deleteById(addressId);
        return "Address id : " + addressId + " deleted";
    }

    public AddressDto createAddress(CreateAddressRequest request) {

        Address address = new Address(
                request.getCountry(),
                request.getCity(),
                request.getStreet(),
                request.getBuildingNumber(),
                request.getApartmentNumber(),
                request.getZipCode()
        );

        return addressDtoConverter.convert(addressRepository.save(address));

    }

    public AddressDto updateAddress(String addressId, UpdateAddressRequest request) {
        Address address = findByAddressId(addressId);

        Address updatedAddress = new Address(
                addressId,
                Optional.ofNullable(request.getCountry()).orElse(address.getCountry()),
                Optional.ofNullable(request.getCity()).orElse(address.getCity()),
                Optional.ofNullable(request.getStreet()).orElse(address.getCity()),
                Optional.ofNullable(request.getBuildingNumber()).orElse(address.getBuildingNumber()),
                Optional.ofNullable(request.getApartmentNumber()).orElse(address.getApartmentNumber()),
                Optional.ofNullable(request.getZipCode()).orElse(address.getZipCode())
        );
        return addressDtoConverter.convert(addressRepository.save(updatedAddress));
    }

    public List<Address> getAddressList() {
        return addressRepository.findAll();
    }

    public List<AddressDto> getAddressDtoList() {
        return addressDtoConverter.convert(getAddressList());
    }

    public List<AddressDto> doFilter(String query) {
        return getAddressDtoList()
                .stream()
                .filter(addressDto -> addressDto
                        .toString()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
