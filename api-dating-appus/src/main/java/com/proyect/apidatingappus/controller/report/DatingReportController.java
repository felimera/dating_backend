package com.proyect.apidatingappus.controller.report;

import com.proyect.apidatingappus.service.report.DatingReportByCustomerService;
import com.proyect.apidatingappus.util.Constants;
import com.proyect.apidatingappus.util.DateUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "api/report")
public class DatingReportController {

    @Autowired
    DatingReportByCustomerService datingReportByCustomerService;

    @GetMapping(path = "/pdf")
    public ResponseEntity<byte[]> exportToPdf(@RequestParam(name = "idCustomer") long idCustomer) throws JRException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDispositionFormData(Constants.NAME_REPORT, Constants.NAME_REPORT.concat(DateUtil.getExportFormaterDateReport(LocalDateTime.now())).concat(Constants.EXTENSION_PDF));
        return ResponseEntity.ok().headers(httpHeaders).body(datingReportByCustomerService.exportToPdf(idCustomer));
    }

    @GetMapping(path = "/xls")
    public ResponseEntity<byte[]> exportToXls(@RequestParam(name = "idCustomer") long idCustomer) throws JRException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
        var contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(Constants.NAME_REPORT.concat(DateUtil.getExportFormaterDateReport(LocalDateTime.now())).concat(Constants.EXTENSION_XLS))
                .build();
        httpHeaders.setContentDisposition(contentDisposition);
        return ResponseEntity.ok().headers(httpHeaders).body(datingReportByCustomerService.exportToXls(idCustomer));
    }
}
