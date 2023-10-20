package com.proyect.apidatingappus.model.entityReport;

import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Getter
@Setter
public class DatingReport {
    private String titleReport;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String consultationDate;
    private String consultationTime;
    private String reportDate;

    private JRBeanCollectionDataSource tableReportList;
}
