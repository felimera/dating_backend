package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.dto.CustomerDto;
import com.proyect.apidatingappus.controller.dto.auth.LoginRequest;
import com.proyect.apidatingappus.controller.mapper.CustomerMapper;
import com.proyect.apidatingappus.exception.precondition.PreconditionsCustomer;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/customer")
public class CustomerController {

    private CustomerService customerService;

    private LoginService loginService;

    @Autowired
    public CustomerController(CustomerService customerService, LoginService loginService) {
        this.customerService = customerService;
        this.loginService = loginService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll() {
        List<CustomerDto> customerDtoList = customerService
                .getAll()
                .stream()
                .map(CustomerMapper.INSTANCE::toDto)
                .toList();
        return ResponseEntity.ok(customerDtoList);
    }

    @PutMapping(value = "/{idCustomer}")
    public ResponseEntity<Object> putCustomer(@PathVariable(name = "idCustomer") Long idCustomer, @Validated @RequestBody CustomerDto customerDto) {
        PreconditionsCustomer.checkNullBodyField(customerDto);
        Customer customer = customerService.putCustomer(idCustomer, CustomerMapper.INSTANCE.toEntity(customerDto));
        CustomerDto dto = CustomerMapper.INSTANCE.toDto(customer);

        LoginRequest request = new LoginRequest();
        request.setEmail(customer.getEmail());
        request.setPassword(customer.getUser().getPassword());
        dto.setToken(loginService.getGenerateTokenWithoutAuthorization(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping(value = "/{idCustomer}")
    public ResponseEntity<Object> getById(@PathVariable(name = "idCustomer") Long idCustomer) {
        Customer customer = customerService.getById(idCustomer);
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toDto(customer));
    }

    @GetMapping(value = "/findemail")
    public ResponseEntity<Object> getByEmail(@RequestParam(name = "email") String email) {
        PreconditionsCustomer.checkNullEmailField(email);
        Customer customer = customerService.getByEmail(email);
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toDto(customer));
    }
}
