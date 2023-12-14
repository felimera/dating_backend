package com.proyect.apidatingappus.util;

import java.util.regex.Pattern;

public class NumberUtils {
    private NumberUtils() {
        throw new IllegalStateException(NumberUtils.class.toString());
    }

    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static String getFormaterPrice(long price) {
        return "$ " + price + ".00";
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
