package com.proyect.apidatingassi.service;

import org.hibernate.sql.ast.tree.update.Assignment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentService {
    List<Assignment> getAll(int order);
}
