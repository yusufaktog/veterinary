package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AddressRepository extends JpaRepository<Address, String> {
    @Modifying
    @Query(value = "DELETE FROM public.owner_addresses WHERE address_id = :addressId AND owner_id = :ownerId", nativeQuery = true)
    void deleteAddressFromOwnerAddresses(@Param("addressId") String addressId, @Param("ownerId") String ownerId);

    @Modifying
    @Query(value = "INSERT INTO public.owner_addresses(owner_id, address_id) VALUES ( :ownerId, :addressId)", nativeQuery = true)
    void addAddressToOwner(@Param("ownerId") String ownerId, @Param("addressId") String addressId);

    @Query(value = "SELECT owner_id,address_id FROM public.owner_addresses where owner_id = :ownerId", nativeQuery = true)
    Optional<Object> getOwnerAddresses(@Param("ownerId") String ownerId);

}
