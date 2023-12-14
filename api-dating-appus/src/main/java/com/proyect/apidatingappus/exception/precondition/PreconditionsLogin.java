package com.proyect.apidatingappus.exception.precondition;

import com.proyect.apidatingappus.controller.dto.auth.LoginRequest;
import com.proyect.apidatingappus.exception.RequestException;

import java.util.Objects;

public class PreconditionsLogin {
    private PreconditionsLogin() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkNullBodyField(LoginRequest loginRequest) {
        if (Objects.isNull(loginRequest.getEmail()) || loginRequest.getEmail().length() == 0) {
            throw new RequestException("401", "The email cannot be null or empty.");
        }
        if (Objects.isNull(loginRequest.getPassword()) || loginRequest.getPassword().length() == 0) {
            throw new RequestException("401", "The password cannot be null or empty.");
        } else if (loginRequest.getPassword().length() < 8) {
            throw new RequestException("401", "The password cannot be less than 8 characters.");
        }
    }
}
