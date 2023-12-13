package com.proyect.apidatingappus.exception.precondition;

import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.util.DateUtil;

public class PreconditionsWorkHours {
    private PreconditionsWorkHours() {
        throw new IllegalStateException(PreconditionsWorkHours.class.toString());
    }

    public static void checkFieldsWithoutDate(String fecha) {
        if (DateUtil.isValidateEntryDate(fecha)) {
            throw new RequestException("401", "Date format error. The date must have the format: 'yyyy-MM-dd'.");
        }
    }
}
