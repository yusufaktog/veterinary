package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Address;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface AddressRepository extends JpaRepository<Address, String> {
    @Modifying
    @Query(value = "DELETE FROM public.owner_addresses WHERE address_id = :addressId AND owner_id = :ownerId", nativeQuery = true)
    void deleteAddressFromOwnerAddresses(@Param("addressId") String addressId, @Param("ownerId") String ownerId);

    @Modifying
    @Query(value = "INSERT INTO public.owner_addresses(owner_id, address_id) VALUES ( :ownerId, :addressId)", nativeQuery = true)
    void addAddressToOwner(@Param("ownerId") String ownerId, @Param("addressId") String addressId);

    @Query(value = "SELECT EXISTS (SELECT FROM public.owner_addresses WHERE (address_id = :addressId))", nativeQuery = true)
    boolean isAddressInUse(@Param("addressId") String addressId);

    Page<Address> findByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrStreetContainingIgnoreCase(
            String country,
            String city,
            String street,
            Pageable pageable);

}