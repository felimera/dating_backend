package com.proyect.apidatingassi.util;

import com.proyect.apidatingassi.exception.RequestException;

public class PreconditionsAssignment {
    public static void checkNullBodyField(String order) {
        if (!NumberUtil.isNumeric(order)) {
            throw new RequestException("403", "The input parameter must be number.");
        }
    }
}
