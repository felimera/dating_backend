package com.proyect.apidatingassi.service;

import com.proyect.apidatingassi.model.Assignment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentService {
    List<Assignment> getAll(int order);
}
