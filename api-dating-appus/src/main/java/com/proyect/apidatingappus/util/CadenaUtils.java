package com.proyect.apidatingappus.util;

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
}
