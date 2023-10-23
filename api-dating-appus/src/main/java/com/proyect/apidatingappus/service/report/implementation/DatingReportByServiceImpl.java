package com.proyect.apidatingappus.service.report.implementation;

import com.proyect.apidatingappus.controller.dto.ReportSearchParametersDto;
import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.entityreport.AssignmentTableReport;
import com.proyect.apidatingappus.model.entityreport.DatingReport;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.service.report.DatingReportByService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DatingReportByServiceImpl implements DatingReportByService {
    @Autowired
    AppointmentRepository appointmentRepository;

    private static void getDataClient(Customer customer, DatingReport datingReport) {
        datingReport.setCustomerName(customer.getFirtName() + " " + customer.getLastName());
        datingReport.setCustomerEmail(customer.getEmail());
        datingReport.setCustomerPhone(customer.getPhone());
    }

    private List<DatingReport> getDatingReportList(List<Appointment> appointmentList) {

        Map<LocalDate, List<Appointment>> appointmentListMap = appointmentList.stream().collect(Collectors.groupingBy(Appointment::getDate));
        return appointmentListMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(DatingReportByServiceImpl::getDatingReport)
                .toList();
    }

    private static DatingReport getDatingReport(Map.Entry<LocalDate, List<Appointment>> localDateListEntry) {
        DatingReport datingReport = new DatingReport();
        datingReport.setTitleReport("Report of client services");
        datingReport.setConsultationDate(localDateListEntry.getValue().get(0).getDate().toString());
        datingReport.setConsultationTime(localDateListEntry.getValue().get(0).getTime());
        datingReport.setReportDate(DateUtil.getFormaterStringReport(LocalDateTime.now()));
        getDataClient(localDateListEntry.getValue().get(0).getCustomer(), datingReport);
        datingReport.setTableReportList(new JRBeanCollectionDataSource(getTableReportList(localDateListEntry.getValue())));
        return datingReport;
    }

    private static List<AssignmentTableReport> getTableReportList(List<Appointment> appointmentList) {
        return appointmentList.stream()
                .map(Appointment::getAssignment)
                .map(assignment ->
                        new AssignmentTableReport(
                                assignment.getName(),
                                assignment.getDescription(),
                                NumberUtils.getFormaterPrice(assignment.getPrice())))
                .toList();
    }

    private JasperPrint reportMain(ReportSearchParametersDto parametersDto) throws JRException {
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/DatingAppUsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);

        List<Appointment> appointmentList = appointmentRepository.getAppointmentListByParameter(parametersDto);
        if (appointmentList.isEmpty())
            throw new RequestException("702", "The client ID does not exist.");

        List<DatingReport> datingReportList = getDatingReportList(appointmentList);
        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datingReportList);
        return JasperFillManager.fillReport(jasperReport, parameters, source);
    }

    @Override
    public byte[] exportToPdf(ReportSearchParametersDto reportSearchParametersDto) throws JRException {
        return JasperExportManager.exportReportToPdf(reportMain(reportSearchParametersDto));
    }

    @Override
    public byte[] exportToXls(ReportSearchParametersDto reportSearchParametersDto) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArrayOutputStream);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(reportMain(reportSearchParametersDto)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArrayOutputStream.toByteArray();
    }
}
