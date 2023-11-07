package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.exception.precondition.PreconditionsSignup;
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
    public ResponseEntity<Object> signUser(@RequestBody SignUpDto signUpDto) {
        PreconditionsSignup.checkNullBodyField(signUpDto);
        boolean isUserCreated = authService.createUser(signUpDto);
        if (isUserCreated)
            return ResponseEntity.status(HttpStatus.CREATED).body("User created succesfully.");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed created user.");
    }
}
