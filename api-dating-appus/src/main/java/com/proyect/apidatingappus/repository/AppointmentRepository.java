package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentReportRepository {
    @Query("select ap from Appointment ap inner join Customer cu on ap.customer.id = cu.id where cu.id = :idCustomer  and ap.valid = 'A' ")
    List<Appointment> findAllByIdCustomer(Long idCustomer);
}
