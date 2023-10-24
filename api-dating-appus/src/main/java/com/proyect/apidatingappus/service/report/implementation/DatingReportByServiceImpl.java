package com.proyect.apidatingappus.service.report.implementation;

import com.proyect.apidatingappus.controller.dto.ReportSearchParametersDto;
import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.controller.dto.entityreport.AssignmentTableReportDto;
import com.proyect.apidatingappus.controller.dto.entityreport.DatingReportDto;
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

    private static void getDataClient(Customer customer, DatingReportDto datingReportDto) {
        datingReportDto.setCustomerName(customer.getFirtName() + " " + customer.getLastName());
        datingReportDto.setCustomerEmail(customer.getEmail());
        datingReportDto.setCustomerPhone(customer.getPhone());
    }

    private List<DatingReportDto> getDatingReportList(List<Appointment> appointmentList) {

        Map<LocalDate, List<Appointment>> appointmentListMap = appointmentList.stream().collect(Collectors.groupingBy(Appointment::getDate));
        return appointmentListMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(DatingReportByServiceImpl::getDatingReport)
                .toList();
    }

    private static DatingReportDto getDatingReport(Map.Entry<LocalDate, List<Appointment>> localDateListEntry) {
        DatingReportDto datingReportDto = new DatingReportDto();
        datingReportDto.setTitleReport("Report of client services");
        datingReportDto.setConsultationDate(localDateListEntry.getValue().get(0).getDate().toString());
        datingReportDto.setConsultationTime(localDateListEntry.getValue().get(0).getTime());
        datingReportDto.setReportDate(DateUtil.getFormaterStringReport(LocalDateTime.now()));
        getDataClient(localDateListEntry.getValue().get(0).getCustomer(), datingReportDto);
        datingReportDto.setTableReportList(new JRBeanCollectionDataSource(getTableReportList(localDateListEntry.getValue())));
        return datingReportDto;
    }

    private static List<AssignmentTableReportDto> getTableReportList(List<Appointment> appointmentList) {
        return appointmentList
                .stream()
                .map(Appointment::getAssignment)
                .map(assignment -> {
                    AssignmentTableReportDto assignmentTableReportDto = getAssignmentTableReport(assignment);
                    assignmentTableReportDto.setTotalPrice(NumberUtils.getFormaterPrice(appointmentList.get(0).getTotalPrice()));
                    return assignmentTableReportDto;
                })
                .toList();
    }

    private static AssignmentTableReportDto getAssignmentTableReport(Assignment assignment) {
        AssignmentTableReportDto assignmentTableReportDto = new AssignmentTableReportDto();
        assignmentTableReportDto.setName(assignment.getName());
        assignmentTableReportDto.setDescription(assignment.getDescription());
        assignmentTableReportDto.setValue(NumberUtils.getFormaterPrice(assignment.getPrice()));
        return assignmentTableReportDto;
    }

    private JasperPrint reportMain(ReportSearchParametersDto parametersDto) throws JRException {
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/DatingAppUsReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);

        List<Appointment> appointmentList = appointmentRepository.getAppointmentListByParameter(parametersDto);
        if (appointmentList.isEmpty())
            throw new RequestException("702", "The client ID does not exist.");

        List<DatingReportDto> datingReportDtoList = getDatingReportList(appointmentList);
        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datingReportDtoList);
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
