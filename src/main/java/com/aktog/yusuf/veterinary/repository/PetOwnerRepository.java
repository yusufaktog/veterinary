package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Pet;
import com.aktog.yusuf.veterinary.entity.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PetOwnerRepository extends JpaRepository<PetOwner, String> {

    Optional<PetOwner> findByEmail(String email);

    Optional<PetOwner> findByPhoneNumber(String phoneNumber);

}
