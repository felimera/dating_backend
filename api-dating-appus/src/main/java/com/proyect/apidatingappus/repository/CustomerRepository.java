package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCriteriaRepository {
    Optional<Customer> findByEmail(String email);
}
