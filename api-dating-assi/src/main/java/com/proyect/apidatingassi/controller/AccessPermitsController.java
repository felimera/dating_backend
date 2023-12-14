package com.proyect.apidatingassi.controller;

import com.proyect.apidatingassi.service.AccessPermitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/accesspermits")
public class AccessPermitsController {

    private AccessPermitsService accessPermitsService;

    @Autowired
    public AccessPermitsController(AccessPermitsService accessPermitsService) {
        this.accessPermitsService = accessPermitsService;
    }

    @GetMapping(path = "/link")
    public ResponseEntity<Object> getAccessPermitsWithoutId() {
        return ResponseEntity.ok(accessPermitsService.getAccessPermitsWithoutId());
    }
}
