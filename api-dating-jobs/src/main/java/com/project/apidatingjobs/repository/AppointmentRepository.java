package com.project.apidatingjobs.repository;

import com.project.apidatingjobs.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select ap from Appointment ap where ap.date < CURDATE() ")
    List<Appointment> getOldRecords();
}
