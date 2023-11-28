package com.proyect.apidatingappus.controller.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSearchParametersDto {
    private Long idCustomer;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fecha;
    private Long idAssignment;
    private String valid;
}
