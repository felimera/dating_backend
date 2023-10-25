package com.proyect.apidatingappus.service.report.implementation;

import com.proyect.apidatingappus.exception.RequestException;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.entitytest.AppointmentBuilder;
import com.proyect.apidatingappus.model.entitytest.AssignmentBuilder;
import com.proyect.apidatingappus.model.entitytest.CustomerBuilder;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestDatingReportByServiceImpl {
    @Mock
    AppointmentRepository appointmentRepository;
    @InjectMocks
    DatingReportByServiceImpl datingReportByServiceImpl;

    Appointment appointment;

    @BeforeEach
    void setUp() {
        Customer customer = CustomerBuilder.builder().build().toCustomer();
        Assignment assignment = AssignmentBuilder.builder().build().toAssignment();
        appointment = AppointmentBuilder.builder().build().toEditCustomerAndAssignment(customer, assignment);
    }

    @DisplayName("Try JUnit to generate PDF report.")
    @Test
    void when_a_pdf_report_is_generated_correctly() throws JRException {

        given(appointmentRepository.getAppointmentListByParameter(any())).willReturn(List.of(appointment));
        byte[] bytes = datingReportByServiceImpl.exportToPdf(any());
        assertThat(bytes).isNotNull();
    }

    @DisplayName("Test JUnit to check if the query returns a result.")
    @Test
    void when_the_query_returns_no_result() {
        given(appointmentRepository.getAppointmentListByParameter(any())).willReturn(Collections.emptyList());
        assertThrows(RequestException.class, () -> datingReportByServiceImpl.exportToPdf(any()));
        verify(appointmentRepository, never()).save(any());
    }

    @DisplayName("Try JUnit to generate XLS report.")
    @Test
    void when_a_xls_report_is_generated_correctly() throws JRException {

        given(appointmentRepository.getAppointmentListByParameter(any())).willReturn(List.of(appointment));
        byte[] bytes = datingReportByServiceImpl.exportToXls(any());
        assertThat(bytes).isNotNull();
    }
}