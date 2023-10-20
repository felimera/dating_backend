package com.proyect.apidatingappus.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SPACE = " ";
    public static final String BEARER = "Bearer".concat(SPACE);
    public static final String NAME_REPORT = "datingReport";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_XLS = ".xls";
}
