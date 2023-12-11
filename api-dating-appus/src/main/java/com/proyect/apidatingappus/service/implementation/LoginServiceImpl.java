package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.auth.LoginRequest;
import com.proyect.apidatingappus.exception.AuthenticationException;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.service.LoginService;
import com.proyect.apidatingappus.service.jwt.UserJwtServiceImpl;
import com.proyect.apidatingappus.util.Constants;
import com.proyect.apidatingappus.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    AuthenticationManager authenticationManager;
    UserJwtServiceImpl userJwtServiceImpl;
    JwtUtil jwtUtil;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, UserJwtServiceImpl userJwtServiceImpl, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userJwtServiceImpl = userJwtServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String getTokenGenerator(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Unauthorized user.", "501", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails;
        try {
            userDetails = userJwtServiceImpl.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException(Constants.MESSAGE_USER_NOT_FOUND, "501", HttpStatus.NOT_FOUND);
        }

        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @Override
    public String getGenerateTokenWithoutAuthorization(LoginRequest loginRequest) {
        UserDetails userDetails;
        try {
            userDetails = userJwtServiceImpl.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException(Constants.MESSAGE_USER_NOT_FOUND, "502", HttpStatus.NOT_FOUND);
        }

        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
