package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.search.CustomerSearchParameterDto;
import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.TipoRole;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.repository.CustomerRepository;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.service.TipoRoleService;
import com.proyect.apidatingappus.service.UserService;
import com.proyect.apidatingappus.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private UserService userService;
    private TipoRoleService tipoRoleService;

    private AppointmentRepository appointmentRepository;

    @Autowired

    public CustomerServiceImpl(CustomerRepository customerRepository, UserService userService, TipoRoleService tipoRoleService, AppointmentRepository appointmentRepository) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.tipoRoleService = tipoRoleService;
        this.appointmentRepository = appointmentRepository;
    }

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
    public Customer putCustomer(Long id, Customer customer, String rol) {
        Customer entity = customerRepository.findById(id).orElseThrow(() -> new RuntimeException(Constants.MESSAGE_USER_NOT_FOUND));
        TipoRole tipoRole = tipoRoleService.getTipoRoleByAcronym(rol);
        entity.setFirtName(customer.getFirtName());
        entity.setLastName(customer.getLastName());
        entity.setPhone(customer.getPhone());
        entity.setEmail(customer.getEmail());
        entity.setGender(customer.getGender());
        entity.setBirthdate(customer.getBirthdate());
        entity.setTipoRole(tipoRole);

        Customer entityCustomer = customerRepository.save(entity);
        userService.putUser(entityCustomer.getUser().getId(), this.addUserForAuthorization(entityCustomer));
        return entityCustomer;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new BusinessException("201", HttpStatus.NOT_FOUND, Constants.MESSAGE_USER_NOT_FOUND));
    }

    @Override
    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new BusinessException("201", HttpStatus.NOT_FOUND, Constants.MESSAGE_USER_NOT_FOUND));
    }

    @Override
    public List<Customer> getConsultCustomerForVariousParameters(CustomerSearchParameterDto dto) {
        List<Customer> customers = customerRepository.getConsultCustomerForVariousParameters(dto);
        if (customers.isEmpty())
            throw new NotFoundException(Constants.MESSAGE_NOT_FOUND, "301", HttpStatus.NOT_FOUND);
        return customers;
    }

    @Override
    public List<Customer> getConsultCustomerInAppointmentForVariousParameters(CustomerSearchParameterDto dto) {
        List<Customer> customerList = appointmentRepository
                .getConsultCustomerInAppointmentForVariousParameters(dto)
                .stream()
                .map(Appointment::getCustomer)
                .distinct()
                .toList();
        if (customerList.isEmpty())
            throw new NotFoundException(Constants.MESSAGE_USER_NOT_FOUND, "701", HttpStatus.NOT_FOUND);
        return customerList;
    }
}
