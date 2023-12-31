package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.controller.dto.search.CustomerSearchParameterDto;
import com.proyect.apidatingappus.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAll();

    Customer postCustomer(Customer customer, Long idUser);

    Customer putCustomer(Long id, Customer customer, String rol);

    Customer getById(Long id);

    Customer getByEmail(String email);

    List<Customer> getConsultCustomerForVariousParameters(CustomerSearchParameterDto dto);

    List<Customer> getConsultCustomerInAppointmentForVariousParameters(CustomerSearchParameterDto dto);
}
