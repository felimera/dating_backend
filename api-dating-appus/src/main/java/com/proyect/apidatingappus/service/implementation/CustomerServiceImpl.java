package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.repository.CustomerRepository;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    UserService userService;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer postCustomer(Customer customer, Long idUser) {
        if (this.isEmailExist(customer.getEmail())) {
            throw new BusinessException("300", HttpStatus.CONFLICT, "This email already exists.");
        }
        User user = userService.findById(idUser);
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    private User addUserForAuthorization(Customer entity) {
        User user = new User();
        user.setName(entity.getFirtName() + " " + entity.getLastName());
        user.setEmail(entity.getEmail());
        return user;
    }

    private boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public Customer putCustomer(Long id, Customer customer) {
        Customer entity = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("The user does not exist."));
        entity.setFirtName(customer.getFirtName());
        entity.setLastName(customer.getLastName());
        entity.setPhone(customer.getPhone());
        entity.setEmail(customer.getEmail());
        entity.setGender(customer.getGender());
        entity.setRol(customer.getRol());
        entity.setBirthdate(customer.getBirthdate());

        Customer entityCustomer = customerRepository.save(entity);
        userService.putUser(this.addUserForAuthorization(entityCustomer));
        return entityCustomer;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new BusinessException("201", HttpStatus.NOT_FOUND, "The user does not exist."));
    }
}
