package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.repository.UserRepository;
import com.proyect.apidatingappus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(User user) {

        if (userRepository.findOneByEmail(user.getEmail()).isPresent())
            return false;

        String hashPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        userRepository.save(user);
        return true;
    }
}
