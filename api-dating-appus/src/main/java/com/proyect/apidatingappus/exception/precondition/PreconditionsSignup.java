package com.proyect.apidatingappus.exception.precondition;

import com.proyect.apidatingappus.controller.dto.auth.ChangePassowrdDto;
import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.util.CadenaUtils;
import com.proyect.apidatingappus.util.DateUtil;

import java.util.Objects;

public class PreconditionsSignup {

    private PreconditionsSignup() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkNullBodyField(SignUpDto signUpDto) {
        if (Objects.isNull(signUpDto.getNombres()) || signUpDto.getNombres().length() == 0) {
            throw new RequestException("401", "The name cannot be null or empty.");
        }
        if (Objects.isNull(signUpDto.getPassword()) || signUpDto.getPassword().length() == 0) {
            throw new RequestException("401", "The last name cannot be null or empty.");
        }
        if (Objects.isNull(signUpDto.getEmail()) || signUpDto.getEmail().length() == 0) {
            throw new RequestException("401", "The email cannot be null or empty.");
        }
        if (Objects.isNull(signUpDto.getRol()) && signUpDto.getRol().length() == 0) {
            throw new RequestException("401", "The role cannot be null or empty.");
        }
        if (Objects.isNull(signUpDto.getGenero()) && signUpDto.getGenero().length() == 0) {
            throw new RequestException("401", "The gender cannot be null or empty.");
        }
        if (Objects.isNull(signUpDto.getFechaNacimiento()) && signUpDto.getFechaNacimiento().length() == 0) {
            throw new RequestException("401", "The date of birth cannot be null or empty.");
        }
        if (DateUtil.isValidateFormatDate(signUpDto.getFechaNacimiento())) {
            throw new RequestException("401", "Date format error. The date must have the format: 'dd/MM/yyyy'.");
        }
    }

    public static void checkNullBodyPasswordField(ChangePassowrdDto dto) {
        if (Objects.isNull(dto.getEmail()) || dto.getEmail().length() == 0) {
            throw new RequestException("401", "The email cannot be null or empty.");
        }
        if (Objects.isNull(dto.getPasswordOld()) || dto.getPasswordOld().length() == 0) {
            throw new RequestException("401", "The original password cannot be empty or null.");
        }
        if (Objects.isNull(dto.getPasswordNew()) || dto.getPasswordNew().length() == 0) {
            throw new RequestException("401", "The new password cannot be empty or null.");
        }

        if (!CadenaUtils.isValidaEmail(dto.getEmail())) {
            throw new RequestException("401", "The email is not formatted correctly.");
        }
    }
}
