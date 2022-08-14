package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
    Optional<Authority> findByName(String name);
}
