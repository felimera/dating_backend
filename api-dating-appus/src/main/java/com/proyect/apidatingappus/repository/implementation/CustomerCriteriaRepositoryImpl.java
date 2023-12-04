package com.proyect.apidatingappus.repository.implementation;

import com.proyect.apidatingappus.controller.dto.search.CustomerSearchParameterDto;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.complement.Rol;
import com.proyect.apidatingappus.repository.CustomerCriteriaRepository;
import com.proyect.apidatingappus.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerCriteriaRepositoryImpl implements CustomerCriteriaRepository {

    private EntityManager em;

    @Autowired
    public CustomerCriteriaRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Customer> getConsultCustomerForVariousParameters(CustomerSearchParameterDto dto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> customerRoot = cq.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(dto.getId()))
            predicates.add(cb.equal(customerRoot.get("id"), dto.getId()));

        if (Objects.nonNull(dto.getFillName())) {
            Expression<String> nameExpression = cb.upper(cb.concat(cb.concat(customerRoot.get("firtName"), Constants.SPACE), customerRoot.get("lastName")));
            String nombreParameter = "%".concat(dto.getFillName().toUpperCase()).concat("%");
            predicates.add(cb.like(nameExpression, nombreParameter));
        }

        if (Objects.nonNull(dto.getEmail())) {
            Expression<String> emailExpression = cb.upper(customerRoot.get("email"));
            String emailParameter = "%".concat(dto.getEmail().toUpperCase()).concat("%");
            predicates.add(cb.like(emailExpression, emailParameter));
        }

        if (!predicates.isEmpty()) {
            predicates.add(cb.notEqual(customerRoot.get("rol"), Rol.A));

            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(customerRoot.get("firtName")));
            return em.createQuery(cq).getResultList();
        }
        return new ArrayList<>();
    }
}
