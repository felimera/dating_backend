package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.controller.dto.search.AppointmentSearchParametersDto;
import com.proyect.apidatingappus.controller.dto.search.CustomerSearchParameterDto;
import com.proyect.apidatingappus.model.Appointment;

import java.util.List;

public interface AppointmentReportRepository {

    List<Appointment> getAppointmentListByParameter(AppointmentSearchParametersDto appointmentSearchParametersDto);

    List<Appointment> getConsultQuoteWithAnyFilters(AppointmentSearchParametersDto appointmentSearchParametersDto);

    List<Appointment> getConsultCustomerInAppointmentForVariousParameters(CustomerSearchParameterDto dto);
}
