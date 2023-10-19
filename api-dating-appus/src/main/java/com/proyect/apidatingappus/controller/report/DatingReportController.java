package com.proyect.apidatingappus.controller.report;

import com.proyect.apidatingappus.service.report.DatingReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/report")
public class DatingReportController {

    @Autowired
    DatingReportService datingReportService;

    @GetMapping(path = "/pdf")
    public ResponseEntity<byte[]> exportToPdf(@RequestParam(name = "idCustomer") long idCustomer) throws JRException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDispositionFormData("datingReport", "datingReport.pdf");
        return ResponseEntity.ok().headers(httpHeaders).body(datingReportService.exportToPdf(idCustomer));
    }
}
