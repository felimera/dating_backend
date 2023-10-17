package com.proyect.apidatingappus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyect.apidatingappus.controller.dto.CustomerDto;
import com.proyect.apidatingappus.controller.mapper.CustomerMapper;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.util.PreconditionsCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll() {
        List<CustomerDto> customerDtoList = customerService
                .getAll()
                .stream()
                .map(CustomerMapper.INSTANCE::toDto)
                .toList();
        return ResponseEntity.ok(customerDtoList);
    }

    @PostMapping
    public ResponseEntity<Object> postCustomer(@Validated @RequestBody CustomerDto customerDto) {
        PreconditionsCustomer.checkNullBodyField(customerDto);
        Customer customer = customerService.postCustomer(CustomerMapper.INSTANCE.toEntity(customerDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.INSTANCE.toDto(customer));
    }

    @PutMapping(value = "/{idCustomer}")
    public ResponseEntity<Object> putCustomer(@PathVariable(name = "idCustomer") Long idCustomer, @Validated @RequestBody CustomerDto customerDto) {
        PreconditionsCustomer.checkNullBodyField(customerDto);
        Customer customer = customerService.putCustomer(idCustomer, CustomerMapper.INSTANCE.toEntity(customerDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.INSTANCE.toDto(customer));
    }

    @GetMapping(value = "/{idCustomer}")
    public ResponseEntity<Object> getById(@PathVariable(name = "idCustomer") Long idCustomer) {
        Customer customer = customerService.getById(idCustomer);
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toDto(customer));
    }
}
