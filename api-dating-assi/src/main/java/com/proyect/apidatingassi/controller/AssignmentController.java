package com.proyect.apidatingassi.controller;

import com.proyect.apidatingassi.controller.dto.AssignmentDto;
import com.proyect.apidatingassi.controller.mapper.AssignmentMapper;
import com.proyect.apidatingassi.model.Assignment;
import com.proyect.apidatingassi.service.AssignmentService;
import com.proyect.apidatingassi.util.PreconditionsAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/assignment")
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "0") String order) {
        PreconditionsAssignment.checkNullBodyField(order);
        List<Assignment> assignmentList = assignmentService.getAll(Integer.parseInt(order));
        List<AssignmentDto> assignmentDtoList = assignmentList.stream().map(AssignmentMapper.INSTANCE::toDto).toList();
        return ResponseEntity.ok(assignmentDtoList);
    }
}
