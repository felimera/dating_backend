package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.repository.UserRepository;
import com.proyect.apidatingappus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User postUser(User user) {
        if (this.isEmailExist(user.getEmail())) {
            throw new BusinessException("300", HttpStatus.CONFLICT, "This email already exists.");
        }

        return userRepository.save(user);
    }

    private boolean isEmailExist(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }

    @Override
    public User putUser(User user) {
        User entity = userRepository.findOneByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("The user does not exist."));
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        return userRepository.save(entity);
    }
}