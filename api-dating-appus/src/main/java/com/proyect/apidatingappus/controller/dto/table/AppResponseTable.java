package com.proyect.apidatingappus.controller.dto.table;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppResponseTable {
    private Long idAppointment;
    private String fecha;
    private String fechaSinFor;
    private String horaSinFor;
    private String precioTotal;
    private List<ContentTable> contentTableList;
    private String idMonth;
    private String month;
    private String fillNameCustomer;
}
