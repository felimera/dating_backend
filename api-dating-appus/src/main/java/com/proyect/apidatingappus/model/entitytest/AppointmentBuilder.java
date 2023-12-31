package com.proyect.apidatingappus.model.entitytest;

import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AppointmentBuilder {
    private Long id;
    private LocalDate date;
    private String time;
    private Long totalPrice;
    private Customer customer;
    private Assignment assignment;
    private String valid;

    private AppointmentBuilder toAppointmentBuilder() {
        return AppointmentBuilder.builder()
                .id(1L)
                .date(LocalDate.now().plusDays(1))
                .time("12:00:00")
                .totalPrice(125L)
                .customer(null)
                .assignment(null)
                .valid("T")
                .build();
    }

    public Appointment toAppointment() {
        AppointmentBuilder builder = toAppointmentBuilder();
        return new Appointment(builder.id, builder.date, builder.time, builder.totalPrice, builder.customer, builder.assignment, builder.valid);
    }

    public Appointment toEditId(Long id) {
        AppointmentBuilder builder = toAppointmentBuilder();
        return new Appointment(id, builder.date, builder.time, builder.totalPrice, builder.customer, builder.assignment, builder.valid);
    }

    public Appointment toEditDate(LocalDate date) {
        AppointmentBuilder builder = toAppointmentBuilder();
        return new Appointment(builder.id, date, builder.time, builder.totalPrice, builder.customer, builder.assignment, builder.valid);
    }

    public Appointment toEditCustomerAndAssignment(Customer customer, Assignment assignment) {
        AppointmentBuilder builder = toAppointmentBuilder();
        return new Appointment(builder.id, builder.date, builder.time, builder.totalPrice, customer, assignment, builder.valid);
    }
}
