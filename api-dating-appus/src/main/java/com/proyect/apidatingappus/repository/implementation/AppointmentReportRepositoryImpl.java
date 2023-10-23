package com.proyect.apidatingappus.repository.implementation;

import com.proyect.apidatingappus.controller.dto.ReportSearchParametersDto;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.repository.AppointmentReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentReportRepositoryImpl implements AppointmentReportRepository {
    @Autowired
    private EntityManager em;

    @Override
    public List<Appointment> getAppointmentListByParameter(ReportSearchParametersDto reportSearchParametersDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);

        Root<Appointment> appointmentRoot = cq.from(Appointment.class);
        List<Predicate> predicates = new ArrayList<>();

        Join<Appointment, Customer> appointmentCustomerJoin = appointmentRoot.join("customer", JoinType.INNER);

        if (Objects.nonNull(reportSearchParametersDto.getIdCustomer()))
            predicates.add(cb.equal(appointmentCustomerJoin.get("id"), reportSearchParametersDto.getIdCustomer()));

        if (Objects.nonNull(reportSearchParametersDto.getFechaInicio()) && Objects.nonNull(reportSearchParametersDto.getFechaFin()))
            predicates.add(cb.between(appointmentRoot.get("date"), reportSearchParametersDto.getFechaInicio(), reportSearchParametersDto.getFechaFin()));

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
