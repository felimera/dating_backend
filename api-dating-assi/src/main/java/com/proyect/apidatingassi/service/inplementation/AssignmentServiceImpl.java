package com.proyect.apidatingassi.service.inplementation;

import com.proyect.apidatingassi.model.Assignment;
import com.proyect.apidatingassi.repository.AssignmentRepository;
import com.proyect.apidatingassi.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    private Sort getWayToOrganizeData(int order) {
        String name;
        switch (order) {
            case 1:
                name = "name";
                break;
            case 2:
                name = "price";
                break;
            case 3:
                name = "description";
                break;
            default:
                name = "id";
                break;
        }
        return Sort.by(Sort.Direction.ASC, name);
    }

    @Override
    public List<Assignment> getAll(int order) {
        return assignmentRepository.findAll(this.getWayToOrganizeData(order));
    }

    @Override
    public Assignment getById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("The service consulted does not exist."));
    }

    @Override
    public List<Assignment> getByListIds(List<Long> ids) {
        return assignmentRepository.getByListIds(ids);
    }
}
