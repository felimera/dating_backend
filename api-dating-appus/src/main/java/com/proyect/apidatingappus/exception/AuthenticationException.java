package com.proyect.apidatingappus.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class AuthenticationException extends RuntimeException {
    private final String code;
    private final HttpStatus httpStatus;

    public AuthenticationException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
