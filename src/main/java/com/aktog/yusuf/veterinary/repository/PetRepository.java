package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, String> {

    Page<Pet> findAllByNameIgnoreCaseOrTypeIgnoreCaseOrGenusIgnoreCaseOrDescriptionContaining(
            String name,
            String type,
            String genus,
            String description,
            Pageable pageable
    );
}
