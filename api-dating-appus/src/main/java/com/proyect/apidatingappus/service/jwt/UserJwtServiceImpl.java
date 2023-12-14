package com.proyect.apidatingappus.service.jwt;

import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserJwtServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Write logic to fetch user from DB
        User entity = userRepository.findOneByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        return new org.springframework.security.core.userdetails.User(entity.getEmail(), entity.getPassword(), Collections.emptyList());
    }
}
