package com.proyect.apidatingappus.util;

import java.util.regex.Pattern;

public class CadenaUtils {
    private CadenaUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String toMayusculas(String valor) {
        if (valor == null || valor.isEmpty()) {
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

}
