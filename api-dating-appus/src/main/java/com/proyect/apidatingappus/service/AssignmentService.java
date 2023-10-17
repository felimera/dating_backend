package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.model.Assignment;
import org.springframework.stereotype.Service;

@Service
public interface AssignmentService {
    Assignment getById(Long id);
}
