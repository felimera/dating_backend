package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.MessageDto;
import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.exception.precondition.PreconditionsSignup;
import com.proyect.apidatingappus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/signup")
public class SignupController {

    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<Object> signUser(@RequestBody SignUpDto signUpDto) {
        PreconditionsSignup.checkNullBodyField(signUpDto);
        boolean isUserCreated = authService.createUser(signUpDto);
        if (isUserCreated)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MessageDto.builder().code(String.valueOf(HttpStatus.CREATED.value())).message("User created succesfully.").build());
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageDto.builder().code(String.valueOf(HttpStatus.BAD_REQUEST.value())).message("Failed created user.").build());
    }
}
