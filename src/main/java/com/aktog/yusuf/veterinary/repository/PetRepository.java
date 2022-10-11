package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<Pet, String> {


    Page<Pet> findByNameContainsOrTypeContainsOrGenusContainsOrOwnerNameContainsOrOwnerSurnameContainsAllIgnoreCase(
            String name,
            String type,
            String genus,
            String ownerName,
            String surname,
            Pageable pageable);
}
