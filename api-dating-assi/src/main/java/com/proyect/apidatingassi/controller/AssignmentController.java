package com.proyect.apidatingassi.controller;

import com.proyect.apidatingassi.controller.dto.AssignmentDto;
import com.proyect.apidatingassi.controller.dto.AssignmentParameter;
import com.proyect.apidatingassi.controller.mapper.AssignmentMapper;
import com.proyect.apidatingassi.model.Assignment;
import com.proyect.apidatingassi.service.AssignmentService;
import com.proyect.apidatingassi.util.PreconditionsAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/assignment")
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "0") String order) {
        int cont = 0;
        PreconditionsAssignment.checkNullBodyField(AssignmentParameter.builder().build().toEditOrder(order));
        List<Assignment> assignmentList = assignmentService.getAll(Integer.parseInt(order));
        List<AssignmentDto> assignmentDtoList = new ArrayList<>();

        for (Assignment assignment : assignmentList) {
            AssignmentDto dto = AssignmentMapper.INSTANCE.toDto(assignment);
            dto.setPosicion(++cont);
            assignmentDtoList.add(dto);
        }
        return ResponseEntity.ok(assignmentDtoList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {
        PreconditionsAssignment.checkNullBodyField(AssignmentParameter.builder().build().toEditId(id));
        Assignment assignment = assignmentService.getById(id);
        AssignmentDto assignmentDto = AssignmentMapper.INSTANCE.toDto(assignment);
        return ResponseEntity.ok(assignmentDto);
    }

    @GetMapping(path = "/")
    public ResponseEntity<Object> getByListIds(@RequestParam(name = "ids") String ids) {
        PreconditionsAssignment.checkNullBodyField(AssignmentParameter.builder().build().toEditListId(ids));
        List<Long> longList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        List<Assignment> assignmentList = assignmentService.getByListIds(longList);
        return ResponseEntity.ok(assignmentList.stream().map(AssignmentMapper.INSTANCE::toDto));
    }
}
