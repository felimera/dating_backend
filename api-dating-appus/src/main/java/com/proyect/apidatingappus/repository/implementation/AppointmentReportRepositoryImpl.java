package com.proyect.apidatingappus.repository.implementation;

import com.proyect.apidatingappus.controller.dto.search.AppointmentSearchParametersDto;
import com.proyect.apidatingappus.controller.dto.search.CustomerSearchParameterDto;
import com.proyect.apidatingappus.model.*;
import com.proyect.apidatingappus.model.complement.Rol;
import com.proyect.apidatingappus.repository.AppointmentReportRepository;
import com.proyect.apidatingappus.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AppointmentReportRepositoryImpl implements AppointmentReportRepository {

    private EntityManager em;

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

        Join<Appointment, Customer> appointmentCustomerJoin = appointmentRoot.join(Appointment_.CUSTOMER, JoinType.INNER);
        Join<Appointment, Assignment> appointmentAssignmentJoin = appointmentRoot.join(Appointment_.ASSIGNMENT, JoinType.INNER);

        predicates.add(cb.equal(appointmentRoot.get(Appointment_.VALID), Constants.A));

        if (Objects.nonNull(appointmentSearchParametersDto.getIdCustomer()))
            predicates.add(cb.equal(appointmentCustomerJoin.get(Customer_.ID), appointmentSearchParametersDto.getIdCustomer()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFechaInicio()) && Objects.nonNull(appointmentSearchParametersDto.getFechaFin()))
            predicates.add(cb.between(appointmentRoot.get(Appointment_.DATE), appointmentSearchParametersDto.getFechaInicio(), appointmentSearchParametersDto.getFechaFin()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFecha()))
            predicates.add(cb.equal(appointmentRoot.get(Appointment_.DATE), appointmentSearchParametersDto.getFecha()));

        if (Objects.nonNull(appointmentSearchParametersDto.getIdAssignment()))
            predicates.add(cb.equal(appointmentAssignmentJoin.get(Assignment_.ID), appointmentSearchParametersDto.getIdAssignment()));

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Appointment> getConsultQuoteWithAnyFilters(AppointmentSearchParametersDto appointmentSearchParametersDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);

        Root<Appointment> appointmentRoot = cq.from(Appointment.class);
        List<Predicate> predicates = new ArrayList<>();

        Join<Appointment, Customer> appointmentCustomerJoin = appointmentRoot.join(Appointment_.CUSTOMER, JoinType.INNER);
        Join<Appointment, Assignment> appointmentAssignmentJoin = appointmentRoot.join(Appointment_.ASSIGNMENT, JoinType.INNER);

        if (Objects.nonNull(appointmentSearchParametersDto.getValid())) {
            if (appointmentSearchParametersDto.getValid().equals("T"))
                predicates.add(cb.in(appointmentRoot.get(Appointment_.VALID)).value(Arrays.asList("T", "A", "V")));
            else
                predicates.add(cb.equal(appointmentRoot.get(Appointment_.VALID), appointmentSearchParametersDto.getValid()));
        }

        if (Objects.nonNull(appointmentSearchParametersDto.getNameCustomer())) {
            Expression<String> fullName = cb.upper(cb.concat(cb.concat(appointmentCustomerJoin.get(Customer_.FIRT_NAME), Constants.SPACE), appointmentCustomerJoin.get(Customer_.LAST_NAME)));
            predicates.add(cb.like(fullName, "%" + appointmentSearchParametersDto.getNameCustomer().toLowerCase() + "%"));
        }
        if (Objects.nonNull(appointmentSearchParametersDto.getIdCustomer()))
            predicates.add(cb.equal(appointmentCustomerJoin.get(Customer_.ID), appointmentSearchParametersDto.getIdCustomer()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFechaInicio()) && Objects.nonNull(appointmentSearchParametersDto.getFechaFin()))
            predicates.add(cb.between(appointmentRoot.get(Appointment_.DATE), appointmentSearchParametersDto.getFechaInicio(), appointmentSearchParametersDto.getFechaFin()));

        if (Objects.nonNull(appointmentSearchParametersDto.getFecha()))
            predicates.add(cb.equal(appointmentRoot.get(Appointment_.DATE), appointmentSearchParametersDto.getFecha()));

        if (Objects.nonNull(appointmentSearchParametersDto.getIdAssignment()))
            predicates.add(cb.equal(appointmentAssignmentJoin.get(Assignment_.ID), appointmentSearchParametersDto.getIdAssignment()));

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Appointment> getConsultCustomerInAppointmentForVariousParameters(CustomerSearchParameterDto dto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);

        Root<Appointment> appointmentRoot = cq.from(Appointment.class);
        Join<Customer, Appointment> customerAppointmentJoin = appointmentRoot.join(Appointment_.CUSTOMER, JoinType.INNER);
        Join<TipoRole, Customer> tipoRoleCustomerJoin = customerAppointmentJoin.join(Customer_.TIPO_ROLE, JoinType.INNER);
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(dto.getFillName())) {
            Expression<String> nameExpression = cb.upper(cb.concat(cb.concat(customerAppointmentJoin.get(Customer_.FIRT_NAME), Constants.SPACE), customerAppointmentJoin.get(Customer_.LAST_NAME)));
            String nombreParameter = "%".concat(dto.getFillName().toUpperCase()).concat("%");
            predicates.add(cb.like(nameExpression, nombreParameter));
        }

        if (!predicates.isEmpty()) {
            predicates.add(cb.notEqual(tipoRoleCustomerJoin.get(TipoRole_.ACRONYM), Rol.A.name()));
            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(customerAppointmentJoin.get(Customer_.FIRT_NAME)));
            return em.createQuery(cq).getResultList();
        }
        return new ArrayList<>();
    }

}
