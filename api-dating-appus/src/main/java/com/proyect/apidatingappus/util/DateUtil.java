package com.proyect.apidatingappus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidateFormatDate(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat(Constants.DATE_FORMAT);
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }

    public static boolean isValidateOldDate(LocalDate dateValidad) {
        return dateValidad.isBefore(LocalDate.now()) || dateValidad.isEqual(LocalDate.now());
    }

    public static String getFormaterStringReport(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM', ' yyyy' del día' dd 'a las' hh:MM 'horas'.");
        return CadenaUtils.toMayusculas(localDateTime.format(formatter));
    }

    public static String getExportFormaterDateReport(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'_'MM'_' dd'_'yyyy'_'hh:MM:SS'_'");
        return CadenaUtils.toMayusculas(localDateTime.format(formatter));
    }

    public static LocalDate getLocalDateToBirthday(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return LocalDate.parse(fecha, formatter);
    }
}
