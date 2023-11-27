package com.proyect.apidatingappus.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SPACE = " ";
    public static final String BEARER = "Bearer".concat(SPACE);
    public static final String NAME_REPORT = "datingReport";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_XLS = ".xls";

    public static final String AMPERSAND = "&";

    public static final String MESSAGE_NOT_FOUND = "No results were found.";
    public static final String MESSAGE_USER_NOT_FOUND = "The user does not exist.";
}
