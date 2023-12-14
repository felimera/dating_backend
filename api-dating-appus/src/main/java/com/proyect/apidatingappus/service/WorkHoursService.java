package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.model.WorkHours;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface WorkHoursService {
    List<WorkHours> getWorkHours(LocalDate selectedDate);
}
