package com.proyect.apidatingappus.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDto {
    private Long id;
    private String fecha;
    private String hora;
    private Long precioTotal;
    private Long idCustomer;
    private Long idAssignment;
}
