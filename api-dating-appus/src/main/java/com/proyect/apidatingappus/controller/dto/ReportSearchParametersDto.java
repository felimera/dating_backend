package com.proyect.apidatingappus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReportSearchParametersDto {
    private Long idCustomer;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
