package com.aktog.yusuf.veterinary.service;


import com.aktog.yusuf.veterinary.dto.AddressDto;
import com.aktog.yusuf.veterinary.dto.converter.AddressDtoConverter;
import com.aktog.yusuf.veterinary.dto.request.create.CreateAddressRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdateAddressRequest;
import com.aktog.yusuf.veterinary.dto.request.update.UpdatePetOwnerRequest;
import com.aktog.yusuf.veterinary.entity.Address;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import com.aktog.yusuf.veterinary.exception.AddressInUseException;
import com.aktog.yusuf.veterinary.repository.AddressRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressDtoConverter addressDtoConverter;
    private final AddressRepository addressRepository;
    private final PetOwnerService petOwnerService;

    public AddressService(AddressDtoConverter addressDtoConverter,
                          AddressRepository addressRepository,
                          PetOwnerService petOwnerService) {
        this.addressDtoConverter = addressDtoConverter;
        this.addressRepository = addressRepository;
        this.petOwnerService = petOwnerService;
    }

    public Address findByAddressId(String addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address id : " + addressId + " could not found"));
    }

    public AddressDto getAddressById(String addressId) {
        return addressDtoConverter.convert(findByAddressId(addressId));
    }

    public void removeAddress(String addressId, String ownerId) {
        findByAddressId(addressId);
        addressRepository.deleteAddressFromOwnerAddresses(addressId, ownerId);
    }

    public void addAddress(String ownerId, String addressId) {
        findByAddressId(addressId);
        PetOwner owner = petOwnerService.findByPetOwnerId(ownerId);

        if (!Optional.ofNullable(owner.getAddresses())
                .orElse(new HashSet<>()).stream().map(Address::getId).
                collect(Collectors.toList()).contains(addressId)) {

            addressRepository.addAddressToOwner(ownerId, addressId);
        }

    }

    public String deleteAddressById(String addressId) {

        findByAddressId(addressId);

        if(addressRepository.isAddressInUse(addressId)){
            throw new AddressInUseException("Can not delete this address while there other users still use it");
        }

        addressRepository.deleteById(addressId);
        return "Address id : " + addressId + " deleted";
    }

    public AddressDto createAddress(CreateAddressRequest request, String ownerId) {
        PetOwner petOwner = petOwnerService.findByPetOwnerId(ownerId);

        Address address = new Address(
                request.getCountry(),
                request.getCity(),
                request.getStreet(),
                request.getBuildingNumber(),
                request.getApartmentNumber(),
                request.getZipCode()
        );

        Set<Address> addresses = Optional.ofNullable(petOwner.getAddresses()).orElse(new HashSet<>());

        Address savedAddress = addressRepository.save(address);
        addresses.add(savedAddress);

        UpdatePetOwnerRequest updatePetOwnerRequest = new UpdatePetOwnerRequest(
                petOwner.getName(),
                petOwner.getSurname(),
                petOwner.getPhoneNumber(),
                petOwner.getEmail(),
                petOwner.getPets(),
                addresses,
                petOwner.getAuthorities()
        );

        petOwnerService.updatePetOwner(ownerId, updatePetOwnerRequest);

        return addressDtoConverter.convert(savedAddress);
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
