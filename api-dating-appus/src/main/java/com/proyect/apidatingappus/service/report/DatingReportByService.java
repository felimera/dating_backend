package com.proyect.apidatingappus.service.report;

import com.proyect.apidatingappus.controller.dto.search.AppointmentSearchParametersDto;
import net.sf.jasperreports.engine.JRException;

public interface DatingReportByService {
    byte[] exportToPdf(AppointmentSearchParametersDto appointmentSearchParametersDto) throws JRException;

    byte[] exportToXls(AppointmentSearchParametersDto appointmentSearchParametersDto) throws JRException;
}
