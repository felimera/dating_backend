package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.controller.dto.search.AppointmentSearchParametersDto;
import com.proyect.apidatingappus.controller.dto.table.AppResponseTable;
import com.proyect.apidatingappus.model.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AppointmentService {
    List<Appointment> getAll();

    Appointment postAppointment(Appointment appointment, List<Long> idList);

    Appointment putAppointment(Long id, Long idCustomer, Long idAssignment, Appointment appointment);

    void deleteAppointment(Long id);

    List<AppResponseTable> getAppointmentByIdCustomer(Long idCustomer);

    Appointment getById(Long id);

    Appointment putAssignmentInAppointment(Long id, List<Long> idAssignments, Long idCustomer);

    boolean deleteAppEditAssignment(Long idCustomer, LocalDate fecha, Long idAssignment);

    List<AppResponseTable> getConsultQuoteWithAnyFilters(AppointmentSearchParametersDto parametersDto);

    Appointment putAdminAppointment(Long id, Long idCustomer, Long idAssignment, Appointment entityNew);
}
