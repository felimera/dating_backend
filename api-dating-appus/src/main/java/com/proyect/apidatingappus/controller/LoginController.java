package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.auth.LoginRequest;
import com.proyect.apidatingappus.controller.dto.auth.LoginResponse;
import com.proyect.apidatingappus.exception.precondition.PreconditionsLogin;
import com.proyect.apidatingappus.service.LoginService;
import com.proyect.apidatingappus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private UserService userService;

    private LoginService loginService;

    @Autowired
    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        PreconditionsLogin.checkNullBodyField(loginRequest);

        userService.isExistUser(loginRequest.getEmail());

        String jwt = loginService.getTokenGenerator(loginRequest);
        // Aditional logic can here be added here if needed.
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
