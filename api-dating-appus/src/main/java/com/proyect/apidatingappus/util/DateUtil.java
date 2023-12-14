package com.proyect.apidatingappus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private DateUtil() {
        throw new IllegalStateException(DateUtil.class.toString());
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

    public static boolean isValidateEntryDate(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat(Constants.DATE_FORMAT_YYYYMMDD);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'_'MM'_' dd'_'yyyy'_'hh:mm:SS'_'");
        return CadenaUtils.toMayusculas(localDateTime.format(formatter));
    }

    public static LocalDate getLocalDateToBirthday(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return LocalDate.parse(fecha, formatter);
    }

    public static String getFormaterStringTable(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM' ' dd ' del 'yyyy' a las' hh:mm 'horas'.");
        return CadenaUtils.toMayusculas(localDateTime.format(formatter));
    }

    public static String getIdMonthByLocalDate(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M");
        return fecha.format(formatter);
    }

    public static String getMonthByLocalDate(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        return fecha.format(formatter);
    }

    public static String getHoursplusOne(String hora) {
        return LocalTime.parse(hora).plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
