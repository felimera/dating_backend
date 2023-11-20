package com.proyect.apidatingappus.controller.dto.table;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppResponseTable {
    private Long idAppointment;
    private String fecha;
    private String precioTotal;
    private List<ContentTable> contentTableList;

}
