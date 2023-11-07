package com.proyect.apidatingappus.controller.dto.auth;

import lombok.Data;

@Data
public class SignUpDto {
    private String nombres;
    private String email;
    private String password;
    private String fechaNacimiento;
    private String genero;
    private String rol;
    private String telefono;
}
