package com.proyect.apidatingappus.service.report.implementation;

import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.entityReport.AssignmentTableReport;
import com.proyect.apidatingappus.model.entityReport.DatingReport;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.service.report.DatingReportService;
import com.proyect.apidatingappus.util.DateUtil;
import com.proyect.apidatingappus.util.NumberUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatingReportServiceImpl implements DatingReportService {
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
        this.getDataClient(appointmentList.get(0).getCustomer(), datingReport);

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
            throw new RuntimeException("The client ID does not exist.");

        DatingReport datingReport = this.getDatingReport(appointmentList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tableReportList", new JRBeanCollectionDataSource(this.getTableReportList(appointmentList)));

        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(datingReport));
        return JasperFillManager.fillReport(jasperReport, parameters, source);
    }

    @Override
    public byte[] exportToPdf(long idCustomer) throws JRException {
        return JasperExportManager.exportReportToPdf(this.reportMain(idCustomer));
    }
}
