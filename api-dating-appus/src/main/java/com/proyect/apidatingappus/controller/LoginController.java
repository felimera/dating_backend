package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.auth.LoginRequest;
import com.proyect.apidatingappus.controller.dto.auth.LoginResponse;
import com.proyect.apidatingappus.exception.precondition.PreconditionsLogin;
import com.proyect.apidatingappus.service.UserService;
import com.proyect.apidatingappus.service.jwt.UserJwtServiceImpl;
import com.proyect.apidatingappus.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserJwtServiceImpl userJwtServiceImpl;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        PreconditionsLogin.checkNullBodyField(loginRequest);

        userService.isExistUser(loginRequest.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails;
        try {
            userDetails = userJwtServiceImpl.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Aditional logic can here be added here if needed.
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
