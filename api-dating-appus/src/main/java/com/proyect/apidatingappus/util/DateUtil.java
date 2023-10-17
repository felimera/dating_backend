package com.proyect.apidatingappus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateUtil {

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
}
