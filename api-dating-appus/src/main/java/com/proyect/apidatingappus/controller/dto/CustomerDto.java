package com.proyect.apidatingappus.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String rol;
    private String genero;
    private String fechaNaciemiento;
}
