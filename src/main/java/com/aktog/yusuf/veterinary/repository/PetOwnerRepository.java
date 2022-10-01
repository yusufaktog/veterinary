package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.PetOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface PetOwnerRepository extends JpaRepository<PetOwner, String> {

    Optional<PetOwner> findByEmail(String email);

    Optional<PetOwner> findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "DELETE FROM public.owner_addresses WHERE owner_id =  :ownerId", nativeQuery = true)
    void clearAddresses(String ownerId);

    Page<PetOwner> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrEmailContainingOrPhoneNumberContaining(
            String name,
            String surname,
            String email,
            String phoneNumber,
            Pageable pageable
    );

}
