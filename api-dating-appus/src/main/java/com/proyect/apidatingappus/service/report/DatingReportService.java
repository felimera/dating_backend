package com.proyect.apidatingappus.service.report;

import net.sf.jasperreports.engine.JRException;

public interface DatingReportService {
    byte[] exportToPdf(long idCustomer) throws JRException;
}
