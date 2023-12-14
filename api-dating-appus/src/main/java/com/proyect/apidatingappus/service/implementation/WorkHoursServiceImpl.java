package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.model.WorkHours;
import com.proyect.apidatingappus.repository.AppointmentRepository;
import com.proyect.apidatingappus.repository.WorkHoursRepository;
import com.proyect.apidatingappus.service.WorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkHoursServiceImpl implements WorkHoursService {

    private AppointmentRepository appointmentRepository;
    private WorkHoursRepository workHoursRepository;

    @Autowired
    public WorkHoursServiceImpl(AppointmentRepository appointmentRepository, WorkHoursRepository workHoursRepository) {
        this.appointmentRepository = appointmentRepository;
        this.workHoursRepository = workHoursRepository;
    }

    @Override
    public List<WorkHours> getWorkHours(LocalDate selectedDate) {
        List<WorkHours> workHoursList = workHoursRepository.findAll();
        List<String> timeList = appointmentRepository.getAllBySelectedDate(selectedDate).stream().map(Appointment::getTime).toList();

        timeList.forEach(time ->
                workHoursList.forEach(workHours -> {
                    if (time.equals(workHours.getValue())) {
                        workHours.setDisplased(Boolean.TRUE);
                    }
                }));
        return workHoursList;
    }
}
