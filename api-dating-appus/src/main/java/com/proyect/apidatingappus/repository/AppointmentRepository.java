package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(
            "select ap from Appointment ap " +
                    "inner join ap.customer cu " +
                    "where " +
                    "cu.id=:idCustomer"
    )
    List<Appointment> getAppointmentByIdCustomer(Long idCustomer);
}
