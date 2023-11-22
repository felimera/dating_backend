package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.search.AppointmentSearchParametersDto;
import com.proyect.apidatingappus.controller.dto.table.AppResponseTable;
import com.proyect.apidatingappus.controller.dto.table.ContentTable;
import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.service.AppointmentService;
import com.proyect.apidatingappus.service.AssignmentService;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.util.Constants;
import com.proyect.apidatingappus.util.DateUtil;
import com.proyect.apidatingappus.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    AssignmentService assignmentService;

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    private Appointment getAppointment(Appointment appointment, Long id) {
        Appointment appoi = new Appointment();
        appoi.setId(appointment.getId());
        appoi.setTime(appointment.getTime());
        appoi.setDate(appointment.getDate());
        appoi.setTotalPrice(appointment.getTotalPrice());
        appoi.setCustomer(appointment.getCustomer());

        appoi.setAssignment(assignmentService.getById(id));
        return appoi;
    }

    @Override
    public Appointment postAppointment(Appointment appointment, List<Long> idList) {
        if (DateUtil.isValidateOldDate(appointment.getDate())) {
            throw new BusinessException("301", HttpStatus.CONFLICT, "The date must be greater than the current date.");
        }

        List<Appointment> appointmentList = idList.stream().map(id -> getAppointment(appointment, id)).toList();
        appointmentRepository.saveAll(appointmentList);

        return appointment;
    }

    @Override
    public Appointment putAppointment(Long id, Long idCustomer, Long idAssignment, Appointment entityNew) {

        Appointment entityOld = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException(Constants.MESSAGE_NOT_FOUND, "601", HttpStatus.NOT_FOUND));

        AppointmentSearchParametersDto dto = new AppointmentSearchParametersDto();
        dto.setIdCustomer(idCustomer);
        dto.setFecha(entityOld.getDate());

        List<Appointment> appointmentList = appointmentRepository.getAppointmentListByParameter(dto);
        if (appointmentList.isEmpty())
            throw new NotFoundException(Constants.MESSAGE_NOT_FOUND, "601", HttpStatus.NOT_FOUND);

        appointmentList.forEach(appOld -> {
            appOld.setDate(entityNew.getDate());
            appOld.setTime(entityNew.getTime());
            appOld.setTotalPrice(entityNew.getTotalPrice());
        });

        return appointmentRepository.saveAll(appointmentList).stream().findFirst().orElseThrow();
    }


    @Override
    public void deleteAppointment(Long id) {
        Appointment entity = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("This quote does not exist."));
        appointmentRepository.delete(entity);
    }

    @Override
    public List<AppResponseTable> getAppointmentByIdCustomer(Long idCustomer) {
        List<AppResponseTable> list = new ArrayList<>();
        List<Appointment> appointmentList = appointmentRepository.findAllByIdCustomer(idCustomer);
        if (appointmentList.isEmpty())
            throw new NotFoundException(Constants.MESSAGE_NOT_FOUND, "601", HttpStatus.NOT_FOUND);

        Map<LocalDate, List<Appointment>> appoinmentMap = appointmentList.stream().collect(Collectors.groupingBy(Appointment::getDate));

        appoinmentMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(element -> {
                    AppResponseTable appResponseTable = new AppResponseTable();
                    appResponseTable.setFecha(getFechaHora(element.getKey(), element.getValue()));

                    List<ContentTable> contentTableList = new ArrayList<>();
                    for (Appointment appointment : element.getValue()) {
                        appResponseTable.setIdAppointment(appointment.getId());
                        appResponseTable.setPrecioTotal(NumberUtils.getFormaterPrice(appointment.getTotalPrice()));

                        contentTableList.add(getContentTable(appointment));
                    }
                    appResponseTable.setContentTableList(contentTableList);

                    list.add(appResponseTable);
                });

        return list;
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("This quote does not exist.", "701", HttpStatus.NOT_FOUND));
    }

    @Override
    public Appointment putAssignmentInAppointment(Long id, List<Long> idAssignments, Long idCustomer) {
        Appointment entityOld = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException(Constants.MESSAGE_NOT_FOUND, "601", HttpStatus.NOT_FOUND));

        AppointmentSearchParametersDto dto = new AppointmentSearchParametersDto();
        dto.setIdCustomer(idCustomer);
        dto.setFecha(entityOld.getDate());

        List<Appointment> appointmentList = appointmentRepository.getAppointmentListByParameter(dto);
        if (appointmentList.isEmpty())
            throw new NotFoundException(Constants.MESSAGE_NOT_FOUND, "601", HttpStatus.NOT_FOUND);

        for (int cont = 0; cont < appointmentList.size(); cont++) {
            Long idAssignment = idAssignments.get(cont);
            appointmentList.get(cont).setAssignment(assignmentService.getById(idAssignment));
        }

        return appointmentRepository.saveAll(appointmentList).stream().findFirst().orElseThrow();
    }

    private static String getFechaHora(LocalDate key, List<Appointment> value) {
        String hora = value.stream().findFirst().orElseThrow().getTime();
        return DateUtil.getFormaterStringTable(key.atTime(LocalTime.parse(hora)));
    }

    private static ContentTable getContentTable(Appointment appointment) {
        return ContentTable
                .builder()
                .idAssignment(appointment.getAssignment().getId())
                .nombre(appointment.getAssignment().getName())
                .descripcion(appointment.getAssignment().getDescription())
                .precio(NumberUtils.getFormaterPrice(appointment.getAssignment().getPrice()))
                .build();
    }
}
