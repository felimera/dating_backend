package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.service.AppointmentService;
import com.proyect.apidatingappus.service.AssignmentService;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Appointment postAppointment(Appointment appointment) {
        if (DateUtil.isValidateOldDate(appointment.getDate())) {
            throw new BusinessException("301", HttpStatus.CONFLICT, "The date must be greater than the current date.");
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment putAppointment(Long id, Long idCustomer, Long idAssignment, Appointment appointment) {
        Appointment entity = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("This quote does not exist."));
        entity.setDate(appointment.getDate());
        entity.setTime(appointment.getTime());
        entity.setTotalPrice(appointment.getTotalPrice());

        entity.setCustomer(customerService.getById(idCustomer));
        entity.setAssignment(assignmentService.getById(idAssignment));
        return appointmentRepository.save(entity);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment entity = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("This quote does not exist."));
        appointmentRepository.delete(entity);
    }
}
