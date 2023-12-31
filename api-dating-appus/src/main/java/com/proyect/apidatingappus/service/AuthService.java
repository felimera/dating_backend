package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.controller.dto.auth.ChangePassowrdDto;
import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;

public interface AuthService {
    boolean createUser(SignUpDto signUpDto);

    boolean updatePassword(ChangePassowrdDto changePassowrdDto);
}
