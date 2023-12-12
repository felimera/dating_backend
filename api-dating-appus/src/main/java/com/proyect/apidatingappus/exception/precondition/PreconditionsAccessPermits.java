package com.proyect.apidatingappus.exception.precondition;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.util.NumberUtils;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class PreconditionsAccessPermits {

    private PreconditionsAccessPermits() {
        throw new IllegalStateException(PreconditionsAccessPermits.class.toString());
    }

    public static void checkNullIdCustumer(String idCustomer) {
        if (Objects.isNull(idCustomer)) {
            throw new BusinessException("801-2", HttpStatus.CONFLICT, "The customer id must not be empty or null.");
        }
        if (!NumberUtils.isNumeric(idCustomer)) {
            throw new BusinessException("801-2", HttpStatus.CONFLICT, "The customer id must be numeric.");
        }
    }
}
