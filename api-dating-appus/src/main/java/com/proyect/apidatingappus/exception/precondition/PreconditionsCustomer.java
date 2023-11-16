package com.proyect.apidatingappus.exception.precondition;

import com.proyect.apidatingappus.controller.dto.CustomerDto;
import com.proyect.apidatingappus.exception.RequestException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreconditionsCustomer {
    private PreconditionsCustomer() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkNullBodyField(CustomerDto customerDto) {
        if (Objects.isNull(customerDto.getNombre()) || customerDto.getNombre().length() == 0) {
            throw new RequestException("401", "The name cannot be null or empty.");
        }
        if (Objects.isNull(customerDto.getApellido()) || customerDto.getApellido().length() == 0) {
            throw new RequestException("401", "The last name cannot be null or empty.");
        }
        if (Objects.isNull(customerDto.getCorreo()) || customerDto.getCorreo().length() == 0) {
            throw new RequestException("401", "The email cannot be null or empty.");
        }
        if (Objects.isNull(customerDto.getRol()) && customerDto.getRol().length() == 0) {
            throw new RequestException("401", "The role cannot be null or empty.");
        }
        if (Objects.isNull(customerDto.getGenero()) && customerDto.getGenero().length() == 0) {
            throw new RequestException("401", "The gender cannot be null or empty.");
        }
        if (Objects.isNull(customerDto.getFechaNacimiento()) && customerDto.getFechaNacimiento().length() == 0) {
            throw new RequestException("401", "The date of birth cannot be null or empty.");
        }
    }

    public static void checkNullEmailField(String email) {
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        if (!mather.find()) {
            throw new RequestException("401", "The email is not valid.");
        }
    }
}
