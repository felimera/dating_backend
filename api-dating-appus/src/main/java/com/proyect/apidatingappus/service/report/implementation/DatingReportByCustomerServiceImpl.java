package com.proyect.apidatingappus.service.report.implementation;

import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.entityreport.AssignmentTableReport;
import com.proyect.apidatingappus.model.entityreport.DatingReport;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.service.report.DatingReportByCustomerService;
import com.proyect.apidatingappus.util.DateUtil;
import com.proyect.apidatingappus.util.NumberUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatingReportByCustomerServiceImpl implements DatingReportByCustomerService {
    @Autowired
    AppointmentRepository appointmentRepository;

    private static void getDataClient(Customer customer, DatingReport datingReport) {
        datingReport.setCustomerName(customer.getFirtName() + " " + customer.getLastName());
        datingReport.setCustomerEmail(customer.getEmail());
        datingReport.setCustomerPhone(customer.getPhone());
    }

    private DatingReport getDatingReport(List<Appointment> appointmentList) {

        DatingReport datingReport = new DatingReport();
        datingReport.setTitleReport("Report of client services");
        datingReport.setConsultationDate(appointmentList.get(0).getDate().toString());
        datingReport.setConsultationTime(appointmentList.get(0).getTime());
        datingReport.setReportDate(DateUtil.getFormaterStringReport(LocalDateTime.now()));
        getDataClient(appointmentList.get(0).getCustomer(), datingReport);
        datingReport.setTableReportList(new JRBeanCollectionDataSource(getTableReportList(appointmentList)));

        return datingReport;
    }

    private static List<AssignmentTableReport> getTableReportList(List<Appointment> appointmentList) {
        return appointmentList.stream()
                .map(Appointment::getAssignment)
                .map(assignment -> new AssignmentTableReport(assignment.getName(), assignment.getDescription(), NumberUtils.getFormaterPrice(assignment.getPrice()))).toList();
    }

    private JasperPrint reportMain(long idCustomer) throws JRException {
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/DatingAppUsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);

        List<Appointment> appointmentList = appointmentRepository.getAppointmentByIdCustomer(idCustomer);
        if (appointmentList.isEmpty())
            throw new RequestException("702","The client ID does not exist.");

        DatingReport datingReport = getDatingReport(appointmentList);
        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(datingReport));
        return JasperFillManager.fillReport(jasperReport, parameters, source);
    }

    @Override
    public byte[] exportToPdf(long idCustomer) throws JRException {
        return JasperExportManager.exportReportToPdf(reportMain(idCustomer));
    }

    @Override
    public byte[] exportToXls(long idCustomer) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArrayOutputStream);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(reportMain(idCustomer)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArrayOutputStream.toByteArray();
    }
}
