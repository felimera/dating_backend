package com.project.apidatingjobs.service.implement;

import com.project.apidatingjobs.model.Appointment;
import com.project.apidatingjobs.repository.AppointmentRepository;
import com.project.apidatingjobs.service.AppointmentService;
import com.project.apidatingjobs.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void statusChangeAppointment() {
        List<Appointment> appointmentList = appointmentRepository.getOldRecords();
        appointmentList.forEach(appointment -> appointment.setValid(Constants.V));
        appointmentRepository.saveAll(appointmentList);
    }
}
