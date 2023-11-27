package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.repository.UserRepository;
import com.proyect.apidatingappus.service.UserService;
import com.proyect.apidatingappus.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User putUser(User user) {
        User entity = userRepository.findOneByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException(Constants.MESSAGE_USER_NOT_FOUND));
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        return userRepository.save(entity);
    }

    @Override
    public User findById(Long idUser) {
        return userRepository.findById(idUser).orElseThrow(() -> new RuntimeException(Constants.MESSAGE_USER_NOT_FOUND));
    }

    @Override
    public boolean isExistUser(String email) {
        Optional<User> userOptional = userRepository.findOneByEmail(email);
        if (!userOptional.isPresent())
            throw new NotFoundException(Constants.MESSAGE_USER_NOT_FOUND, "415", HttpStatus.NOT_FOUND);
        return true;
    }
}
