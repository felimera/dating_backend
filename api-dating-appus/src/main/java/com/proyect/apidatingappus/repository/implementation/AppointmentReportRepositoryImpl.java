package com.proyect.apidatingappus.repository.implementation;

import com.proyect.apidatingappus.controller.dto.search.AppointmentSearchParametersDto;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.repository.AppointmentReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AppointmentReportRepositoryImpl implements AppointmentReportRepository {

    private EntityManager em;

    private static final String VALID = "valid";

    @Autowired
    public AppointmentReportRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Appointment> getAppointmentListByParameter(AppointmentSearchParametersDto appointmentSearchParametersDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);

        Root<Appointment> appointmentRoot = cq.from(Appointment.class);
        List<Predicate> predicates = new ArrayList<>();

        Join<Appointment, Customer> appointmentCustomerJoin = appointmentRoot.join("customer", JoinType.INNER);
        Join<Appointment, Assignment> appointmentAssignmentJoin = appointmentRoot.join("assignment", JoinType.INNER);

        predicates.add(cb.equal(appointmentRoot.get(VALID), "T"));

        if (Objects.nonNull(appointmentSearchParametersDto.getIdCustomer()))
            predicates.add(cb.equal(appointmentCustomerJoin.get("id"), appointmentSearchParametersDto.getIdCustomer()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFechaInicio()) && Objects.nonNull(appointmentSearchParametersDto.getFechaFin()))
            predicates.add(cb.between(appointmentRoot.get("date"), appointmentSearchParametersDto.getFechaInicio(), appointmentSearchParametersDto.getFechaFin()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFecha()))
            predicates.add(cb.equal(appointmentRoot.get("date"), appointmentSearchParametersDto.getFecha()));

        if (Objects.nonNull(appointmentSearchParametersDto.getIdAssignment()))
            predicates.add(cb.equal(appointmentAssignmentJoin.get("id"), appointmentSearchParametersDto.getIdAssignment()));

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Appointment> getConsultQuoteWithAnyFilters(AppointmentSearchParametersDto appointmentSearchParametersDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);

        Root<Appointment> appointmentRoot = cq.from(Appointment.class);
        List<Predicate> predicates = new ArrayList<>();

        Join<Appointment, Customer> appointmentCustomerJoin = appointmentRoot.join("customer", JoinType.INNER);
        Join<Appointment, Assignment> appointmentAssignmentJoin = appointmentRoot.join("assignment", JoinType.INNER);

        if (Objects.nonNull(appointmentSearchParametersDto.getValid())) {
            if (appointmentSearchParametersDto.getValid().equals("T"))
                predicates.add(cb.in(appointmentRoot.get(VALID)).value(Arrays.asList("T", "A", "V")));
            else
                predicates.add(cb.equal(appointmentRoot.get(VALID), appointmentSearchParametersDto.getValid()));
        }

        if (Objects.nonNull(appointmentSearchParametersDto.getNameCustomer())) {
            Expression<String> fullName = cb.upper(cb.concat(appointmentCustomerJoin.get("firtName"), appointmentCustomerJoin.get("lastName")));
            predicates.add(cb.like(fullName, "%" + appointmentSearchParametersDto.getNameCustomer().toLowerCase() + "%"));
        }
        if (Objects.nonNull(appointmentSearchParametersDto.getIdCustomer()))
            predicates.add(cb.equal(appointmentCustomerJoin.get("id"), appointmentSearchParametersDto.getIdCustomer()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFechaInicio()) && Objects.nonNull(appointmentSearchParametersDto.getFechaFin()))
            predicates.add(cb.between(appointmentRoot.get("date"), appointmentSearchParametersDto.getFechaInicio(), appointmentSearchParametersDto.getFechaFin()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFecha()))
            predicates.add(cb.equal(appointmentRoot.get("date"), appointmentSearchParametersDto.getFecha()));

        if (Objects.nonNull(appointmentSearchParametersDto.getIdAssignment()))
            predicates.add(cb.equal(appointmentAssignmentJoin.get("id"), appointmentSearchParametersDto.getIdAssignment()));

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
