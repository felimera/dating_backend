package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.controller.dto.auth.LoginRequest;

public interface LoginService {

    String getTokenGenerator(LoginRequest loginRequest);

    String  getGenerateTokenWithoutAuthorization(LoginRequest loginRequest);
}
