package com.proyect.apidatingappus.repository.implementation;

import com.proyect.apidatingappus.model.*;
import com.proyect.apidatingappus.repository.AccessPermitsCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccessPermitsCriteriaRepositoryImpl implements AccessPermitsCriteriaRepository {

    private EntityManager em;

    @Autowired
    public AccessPermitsCriteriaRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<AccessPermits> getAccessPermitsByIdCustomer(Long idCustomer) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessPermits> cq = cb.createQuery(AccessPermits.class);

        Root<AccessPermits> accessPermitsRoot = cq.from(AccessPermits.class);

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Customer> customerSubRoot = subquery.from(Customer.class);
        Join<Customer, User> customerUserJoin = customerSubRoot.join(Customer_.USER, JoinType.INNER);
        subquery.select(customerUserJoin.get(User_.ID));
        subquery.where(cb.equal(customerSubRoot.get(Customer_.ID), idCustomer));

        cq.where(cb.in(accessPermitsRoot.get(AccessPermits_.USER).get(User_.ID)).value(subquery));
        return em.createQuery(cq).getResultList();
    }
}
