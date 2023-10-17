package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.Assignment;
import com.proyect.apidatingappus.repository.AssignmentRepository;
import com.proyect.apidatingappus.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    @Override
    public Assignment getById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new BusinessException("201", HttpStatus.NOT_FOUND, "The assignment does not exist."));
    }
}
