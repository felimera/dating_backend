package com.proyect.apidatingassi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getMonthsByItem(int mesItem) {
        LocalDate fecha = LocalDate.of(1, mesItem, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        return fecha.format(formatter);
    }
}
