package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.model.entitytest.AssignmentBuilder;
import com.proyect.apidatingappus.repository.AssignmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestAssignmentServiceImpl {
    @Mock
    AssignmentRepository assignmentRepository;
    @InjectMocks
    AssignmentServiceImpl assignmentServiceImpl;

    @DisplayName("Test JUnit for the getById method. When it returns a result.")
    @Test
    void when_you_searched_for_the_Assignment_id() {
        Assignment assignment = AssignmentBuilder.builder().build().toAssignment();
        given(assignmentRepository.findById(anyLong())).willReturn(Optional.of(assignment));
        Assignment service = assignmentServiceImpl.getById(anyLong());
        assertThat(service).isNotNull();
    }

    @DisplayName("Test JUnit for the getById method. When you don't find results.")
    @Test
    void when_there_is_no_result_for_the_Assignment_id() {
        given(assignmentRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> assignmentServiceImpl.getById(anyLong()));
        verify(assignmentRepository, never()).save(any(Assignment.class));
    }
}