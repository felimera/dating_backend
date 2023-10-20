package com.proyect.apidatingappus.util;

public class NumberUtils {
    private NumberUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static String getFormaterPrice(long price) {
        return "$ " + price + ".00";
    }
}
