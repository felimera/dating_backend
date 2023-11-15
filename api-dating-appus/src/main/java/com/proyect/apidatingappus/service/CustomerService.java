package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAll();

    Customer postCustomer(Customer customer, Long idUser);

    Customer putCustomer(Long id, Customer customer);

    Customer getById(Long id);

    Customer getByEmail(String email);
}
