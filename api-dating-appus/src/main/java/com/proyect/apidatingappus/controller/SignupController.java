package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.UserDto;
import com.proyect.apidatingappus.controller.mapper.UserMapper;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/signup")
public class SignupController {

    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<Object> signUser(@RequestBody UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        boolean isUserCreated = authService.createUser(user);
        if (isUserCreated)
            return ResponseEntity.status(HttpStatus.CREATED).body("User created succesfully.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed created user.");
    }
}
