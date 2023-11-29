package com.proyect.apidatingassi.controller;

import com.proyect.apidatingassi.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/generic")
public class GenericController {

    private GenericService genericService;

    @Autowired
    public GenericController(GenericService genericService) {
        this.genericService = genericService;
    }

    @GetMapping(path = "/meses")
    public ResponseEntity<Object> getMesesDtoList() {
        return ResponseEntity.ok(genericService.getMesesDtoList());
    }
}
