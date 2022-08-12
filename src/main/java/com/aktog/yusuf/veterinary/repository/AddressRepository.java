package com.aktog.yusuf.veterinary.repository;

import com.aktog.yusuf.veterinary.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,String> {
}
