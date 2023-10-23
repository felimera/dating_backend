package com.proyect.apidatingappus.service.report;

import com.proyect.apidatingappus.controller.dto.ReportSearchParametersDto;
import net.sf.jasperreports.engine.JRException;

public interface DatingReportByService {
    byte[] exportToPdf(ReportSearchParametersDto reportSearchParametersDto) throws JRException;

    byte[] exportToXls(ReportSearchParametersDto reportSearchParametersDto) throws JRException;
}
