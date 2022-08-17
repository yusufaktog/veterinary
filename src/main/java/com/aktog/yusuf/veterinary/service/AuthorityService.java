package com.aktog.yusuf.veterinary.service;

import com.aktog.yusuf.veterinary.entity.Authority;
import com.aktog.yusuf.veterinary.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }



}
