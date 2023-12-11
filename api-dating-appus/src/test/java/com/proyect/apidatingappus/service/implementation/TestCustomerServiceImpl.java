package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.model.complement.Rol;
import com.proyect.apidatingappus.model.entitytest.CustomerBuilder;
import com.proyect.apidatingappus.model.entitytest.UserBuilder;
import com.proyect.apidatingappus.repository.CustomerRepository;
import com.proyect.apidatingappus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestCustomerServiceImpl {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    UserService userService;
    @InjectMocks
    CustomerServiceImpl customerServiceImpl;
    Customer customer;
    User user;

    @BeforeEach
    void setUp() {
        customer = CustomerBuilder.builder().build().toCustomer();
        user = UserBuilder.builder().build().toUser();
    }

    @DisplayName("Test JUnit for the getById method. When it returns a result.")
    @Test
    void when_you_searched_by_the_customer_id() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.of(customer));
        Customer service = customerServiceImpl.getById(anyLong());
        assertThat(service).isNotNull();
    }

    @DisplayName("Test JUnit for the getById method. When you don't find results.")
    @Test
    void when_there_is_no_result_for_the_customer_id() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> customerServiceImpl.getById(anyLong()));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @DisplayName("Test JUnit for the getAll method. When it returns a result.")
    @Test
    void when_you_searched_the_list_of_customer_data() {
        given(customerRepository.findAll()).willReturn(List.of(customer));
        List<Customer> list = customerServiceImpl.getAll();
        assertThat(list).isNotNull();
        assertFalse(list.isEmpty());
    }

    @DisplayName("Test JUnit for the getAll method. When you don't find results.")
    @Test
    void when_there_is_no_result_in_the_customer_list() {
        given(customerRepository.findAll()).willReturn(Collections.emptyList());
        List<Customer> list = customerServiceImpl.getAll();
        assertTrue(list.isEmpty());
    }

    @DisplayName("Try JUnit to save the logs in the PostCustomer method.")
    @Test
    void when_a_record_is_saved_correctly() {
        given(customerRepository.save(Mockito.any())).willReturn(customer);
        Customer postCustomer = customerServiceImpl.postCustomer(customer, anyLong());
        assertThat(postCustomer).isNotNull();
    }

    @DisplayName("Test JUnit for the potCustomer method. When there is a repeated email.")
    @Test
    void when_there_is_a_repeated_email() {
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));
        assertThrows(BusinessException.class, () -> customerServiceImpl.postCustomer(customer, anyLong()));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @DisplayName("Try JUnit to update a record in PutCustomer.")
    @Test
    void when_a_record_is_updated_successfully() {
        Customer customer1 = CustomerBuilder.builder().build().toEditUser(user);
        given(customerRepository.findById(anyLong())).willReturn(Optional.of(customer1));
        given(customerRepository.save(any(Customer.class))).willReturn(customer1);
        given(userService.putUser(anyLong(), any())).willReturn(user);
        customer.setBirthdate(LocalDate.now().plusMonths(5));
        customer.setEmail("nombre@nombre.com");
        Customer putCustomer = customerServiceImpl.putCustomer(anyLong(), customer, Rol.U.name());
        assertThat(putCustomer).isNotNull();
        assertThat(putCustomer.getBirthdate()).isEqualTo(LocalDate.now().plusMonths(5));
        assertThat(putCustomer.getEmail()).isEqualTo("nombre@nombre.com");
    }

    @DisplayName("Try JUnit to update a record in PutCustomer. When the query does not return a result.")
    @Test
    void when_the_customer_id_returns_no_results() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerServiceImpl.putCustomer(anyLong(), customer, Rol.U.name()));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @DisplayName("Test to validate the query by email. If you return something, ask it.")
    @Test
    void when_the_email_query_returns_a_record() {
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));
        Customer customer1 = customerServiceImpl.getByEmail(anyString());
        assertNotNull(customer1);
    }

    @DisplayName("Test to validate the query by email. The question does not return anything.")
    @Test
    void when_the_email_query_does_not_return_a_record() {
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerServiceImpl.getByEmail(anyString()));
        verify(customerRepository, never()).save(any(Customer.class));
    }
}