package com.proyect.apidatingappus.util;

import com.proyect.apidatingappus.model.Customer;

import java.util.Objects;
import java.util.regex.Pattern;

public class CadenaUtils {
    private CadenaUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String toMayusculas(String valor) {

        if (Objects.isNull(valor) || valor.isEmpty()) {
            return valor;
        } else {
            return valor.toUpperCase().charAt(0) + valor.substring(1, valor.length()).toLowerCase();
        }

    }

    public static boolean isValidaEmail(String email) {
        var pattern = Pattern.compile(Constants.PATTERN_EMAIL);
        var matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getFillNameCustomer(Customer customer) {
        if (Objects.nonNull(customer)) {
            return toMayusculas(customer.getFirtName())
                    .concat(Constants.SPACE)
                    .concat(toMayusculas(customer.getLastName()));
        }
        return "";
    }
}
