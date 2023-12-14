package com.project.apidatingjobs.cron;

import com.project.apidatingjobs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppointmentScheduled {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentScheduled(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Scheduled(cron = "${cron.scheduled}")
    public void statusChangeAppointmentTask() {
        appointmentService.statusChangeAppointment();
    }
}
