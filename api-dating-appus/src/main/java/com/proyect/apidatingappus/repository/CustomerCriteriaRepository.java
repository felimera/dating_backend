package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.controller.dto.search.CustomerSearchParameterDto;
import com.proyect.apidatingappus.model.Customer;

import java.util.List;

public interface CustomerCriteriaRepository {
    List<Customer> getConsultCustomerForVariousParameters(CustomerSearchParameterDto dto);
}
