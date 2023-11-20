package com.proyect.apidatingappus.controller.dto.table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentTable {
    private Long idAssignment;
    private String nombre;
    private String descripcion;
    private String precio;
}