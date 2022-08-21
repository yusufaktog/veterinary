package com.aktog.yusuf.veterinary.service;

import com.aktog.yusuf.veterinary.entity.PetOwner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private final PetOwnerService petOwnerService;

    public MyUserDetailsService(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<PetOwner> optionalPetOwner = petOwnerService.findByEmail(email);

        if (optionalPetOwner.isEmpty()) {
            throw new EntityNotFoundException("Entity with email: " + email + " could not found!");
        }

        PetOwner petOwner = optionalPetOwner.get();

        List<GrantedAuthority> grantedAuthorities = petOwner.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new User(petOwner.getEmail(), petOwner.getPassword(), grantedAuthorities);
    }
}
