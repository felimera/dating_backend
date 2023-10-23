package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.controller.dto.ReportSearchParametersDto;
import com.proyect.apidatingappus.model.Appointment;

import java.util.List;

public interface AppointmentReportRepository {

    List<Appointment> getAppointmentListByParameter(ReportSearchParametersDto reportSearchParametersDto);
}
