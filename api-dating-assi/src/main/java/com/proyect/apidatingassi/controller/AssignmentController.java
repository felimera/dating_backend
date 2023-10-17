package com.proyect.apidatingassi.controller;

import com.proyect.apidatingassi.controller.dto.AssignmentDto;
import com.proyect.apidatingassi.controller.mapper.AssignmentMapper;
import com.proyect.apidatingassi.model.Assignment;
import com.proyect.apidatingassi.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/assignment")
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "0") int order) {
        List<Assignment> assignmentList = assignmentService.getAll(order);
        List<AssignmentDto> assignmentDtoList = assignmentList.stream().map(AssignmentMapper.INSTANCE::toDto).toList();
        return ResponseEntity.ok(assignmentDtoList);
    }
}
