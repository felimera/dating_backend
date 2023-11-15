package com.proyect.apidatingassi.controller.dto;

import lombok.Data;

@Data
public class AssignmentDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private long precio;
    private  int posicion;
}
