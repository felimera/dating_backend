package com.proyect.apidatingappus.service.report;

import net.sf.jasperreports.engine.JRException;

public interface DatingReportByCustomerService {
    byte[] exportToPdf(long idCustomer) throws JRException;

    byte[] exportToXls(long idCustomer) throws JRException;
}
