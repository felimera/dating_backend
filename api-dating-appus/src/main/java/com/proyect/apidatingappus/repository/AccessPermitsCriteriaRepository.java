package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.AccessPermits;

import java.util.List;

public interface AccessPermitsCriteriaRepository {
    List<AccessPermits> getAccessPermitsByIdCustomer(Long idCustomer);
}
