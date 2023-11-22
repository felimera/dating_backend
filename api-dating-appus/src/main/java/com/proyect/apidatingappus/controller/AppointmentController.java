package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.AppointmentDto;
import com.proyect.apidatingappus.controller.mapper.AppointmentMapper;
import com.proyect.apidatingappus.exception.precondition.PreconditionsAppointment;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll() {
        List<AppointmentDto> appointmentDtoList = appointmentService
                .getAll()
                .stream()
                .map(AppointmentMapper.INSTANCE::toDto)
                .toList();
        return ResponseEntity.ok(appointmentDtoList);
    }

    @PostMapping
    public ResponseEntity<Object> postAppointment(@RequestBody AppointmentDto appointmentDto) {
        PreconditionsAppointment.checkNullBodyField(appointmentDto);
        Appointment appointment = appointmentService.postAppointment(AppointmentMapper.INSTANCE.toEntity(appointmentDto), appointmentDto.getIdsAssignment());
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.INSTANCE.toDto(appointment));
    }

    @PutMapping(value = "/{idAppointment}")
    public ResponseEntity<Object> putAppointment(@PathVariable(name = "idAppointment") Long idAppointment, @RequestBody AppointmentDto appointmentDto) {
        PreconditionsAppointment.checkFieldsWithoutCustomerIdAndAssignmentId(appointmentDto);
        Appointment appointment = appointmentService.putAppointment(idAppointment, appointmentDto.getIdCustomer(), appointmentDto.getIdAssignment(), AppointmentMapper.INSTANCE.toEntity(appointmentDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.INSTANCE.toDto(appointment));
    }

    @DeleteMapping(value = "/{idAppointment}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable(name = "idAppointment") Long idAppointment) {
        appointmentService.deleteAppointment(idAppointment);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/customer")
    public ResponseEntity<Object> getAppointmentByIdCustomer(@RequestParam(name = "idCustomer") Long idCustomer) {
        return ResponseEntity.ok(appointmentService.getAppointmentByIdCustomer(idCustomer));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {
        Appointment entity = appointmentService.getById(id);
        return ResponseEntity.ok(AppointmentMapper.INSTANCE.toDto(entity));
    }

    @PutMapping(value = "/editassignment/{idAppointment}")
    public ResponseEntity<Object> putAssignmentInAppointment(@PathVariable(name = "idAppointment") Long idAppointment, @RequestBody AppointmentDto appointmentDto) {
        PreconditionsAppointment.checkTheCustomerIdFieldsAndTheAssignmentIdList(appointmentDto);
        Appointment appointment = appointmentService.putAssignmentInAppointment(idAppointment, appointmentDto.getIdsAssignment(), appointmentDto.getIdCustomer());
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.INSTANCE.toDto(appointment));
    }
}
