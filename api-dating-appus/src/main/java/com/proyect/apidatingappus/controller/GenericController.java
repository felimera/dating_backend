package com.proyect.apidatingappus.controller;

import com.proyect.apidatingappus.controller.mapper.WorkHoursMapper;
import com.proyect.apidatingappus.exception.precondition.PreconditionsWorkHours;
import com.proyect.apidatingappus.model.WorkHours;
import com.proyect.apidatingappus.service.WorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/generic")
public class GenericController {

    private WorkHoursService workHoursService;

    @Autowired
    public GenericController(WorkHoursService workHoursService) {
        this.workHoursService = workHoursService;
    }

    @GetMapping(path = "/worktime")
    public ResponseEntity<Object> getWorkHours(@RequestParam(name = "selectedDate") String selectedDate) {
        PreconditionsWorkHours.checkFieldsWithoutDate(selectedDate);
        List<WorkHours> workHoursList = workHoursService.getWorkHours(LocalDate.parse(selectedDate));
        return ResponseEntity.ok(workHoursList.stream().map(WorkHoursMapper.INSTANCE::toDto).toList());
    }
}
