package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.table.AppResponseTable;
import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.model.entitytest.AppointmentBuilder;
import com.proyect.apidatingappus.model.entitytest.AssignmentBuilder;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.service.AssignmentService;
import com.proyect.apidatingappus.service.CustomerService;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAppointmentServiceImpl {

    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    AssignmentService assignmentService;
    @Mock
    CustomerService customerService;
    @InjectMocks
    AppointmentServiceImpl appointmentService;

    Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = AppointmentBuilder.builder().build().toAppointment();
    }

    @DisplayName("Test JUnit for the GetAll method.")
    @Test
    public void when_the_query_returns_somethingget() {
        Appointment appointment2 = AppointmentBuilder.builder().build().toEditId(2L);
        given(appointmentRepository.findAll()).willReturn(List.of(appointment, appointment2));
        List<Appointment> appointmentList = appointmentService.getAll();
        assertThat(appointmentList).isNotNull();
        assertThat(appointmentList.size()).isEqualTo(2);
    }

    @DisplayName("Try JUnit to save the logs in the PostAppointment method.")
    @Test
    public void when_a_record_is_saved_correctly() {
        given(appointmentRepository.saveAll(Mockito.anyCollection())).willReturn(List.of(appointment, appointment));
        Appointment postAppointment = appointmentService.postAppointment(appointment, List.of(1L, 2L));
        assertThat(postAppointment).isNotNull();
    }

    @DisplayName("Test JUnit to validate the registration date in the PostAppointment method.")
    @Test
    public void when_the_registration_date_is_incorrect() {
        Appointment appointment1 = AppointmentBuilder.builder().build().toEditDate(LocalDate.now());
        assertThrows(BusinessException.class, () -> appointmentService.postAppointment(appointment1, List.of(1L, 2L)));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @DisplayName("Try JUnit to update a record in PutAppointment.")
    @Test
    public void when_a_record_is_updated_successfully() {
        given(appointmentRepository.save(appointment)).willReturn(appointment);
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.of(appointment));
        appointment.setDate(LocalDate.now().plusMonths(2));
        appointment.setTime("14:35:14");
        Appointment putAppointment = appointmentService.putAppointment(anyLong(), 1L, 2L, appointment);
        assertThat(putAppointment).isNotNull();
        assertThat(putAppointment.getDate()).isEqualTo(LocalDate.now().plusMonths(2));
        assertThat(putAppointment.getTime()).isEqualTo("14:35:14");
    }

    @DisplayName("Test JUnit to delete a record in the deleteAppointment method.")
    @Test
    public void when_a_record_is_deleted_successfully() {
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.of(appointment));
        willDoNothing().given(appointmentRepository).delete(appointment);
        appointmentService.deleteAppointment(anyLong());
        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @DisplayName("Test JUnit to validate existence of a record in PutAppointment.")
    @Test
    public void when_you_search_for_a_non_existent_record_in_PutAppointment() {
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> appointmentService.putAppointment(anyLong(), 1L, 2L, appointment));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @DisplayName("Test JUnit to validate existence of a record in DeleteAppointment..")
    @Test
    public void when_you_search_for_a_non_existent_record_in_DeleteAppointment() {
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> appointmentService.deleteAppointment(anyLong()));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @DisplayName("Test JUnit to validate the table query.")
    @Test
    void when_the_data_returns_a_list_of_results() {
        Assignment assignment = AssignmentBuilder.builder().build().toAssignment();
        Appointment entity = AppointmentBuilder.builder().build().toEditCustomerAndAssignment(null, assignment);
        given(appointmentRepository.findAllByIdCustomer(anyLong())).willReturn(Collections.singletonList(entity));
        List<AppResponseTable> appResponseTables = appointmentService.getAppointmentByIdCustomer(anyLong());
        assertThat(appResponseTables).isNotNull();
        assertFalse(appResponseTables.isEmpty());
        assertThat(appResponseTables.size()).isEqualTo(1);
    }

    @DisplayName("Test JUnit to validate the table query.")
    @Test
    void when_the_data_does_not_return_a_list_of_results() {
        given(appointmentRepository.findAllByIdCustomer(anyLong())).willReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> appointmentService.getAppointmentByIdCustomer(anyLong()));
        verify(appointmentRepository).findAllByIdCustomer(anyLong());
    }
}