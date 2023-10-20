package com.proyect.apidatingappus.exception.precondition;

import com.proyect.apidatingappus.controller.dto.AppointmentDto;
import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.util.Constants;
import com.proyect.apidatingappus.util.DateUtil;

import java.util.Objects;

public class PreconditionsAppointment {

    public static void checkNullBodyField(AppointmentDto appointmentDto) {
        if (Objects.isNull(appointmentDto.getFecha()) || appointmentDto.getFecha().length() == 0) {
            throw new RequestException("402", "The purchase date cannot be empty or null.");
        }
        if (Objects.isNull(appointmentDto.getHora()) || appointmentDto.getHora().length() == 0) {
            throw new RequestException("402", "The purchase time cannot be empty or null.");
        }
        if (Objects.isNull(appointmentDto.getPrecioTotal()) || appointmentDto.getPrecioTotal() <= 0) {
            throw new RequestException("402", "The total purchase price cannot be empty or less than or equal to zero.");
        }
        if (Objects.isNull(appointmentDto.getIdCustomer())) {
            throw new RequestException("402", "The client id cannot be empty or null.");
        }
        if (Objects.isNull(appointmentDto.getIdAssignment())) {
            throw new RequestException("402", "The service id cannot be empty or null.");
        }

        if (DateUtil.isValidateFormatDate(appointmentDto.getFecha())) {
            throw new RequestException("403", "The date format is incorrect. \nThe date must be in the format : " + Constants.DATE_FORMAT);
        }
    }
}
