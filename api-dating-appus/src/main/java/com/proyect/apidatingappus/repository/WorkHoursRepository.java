package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.WorkHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHours,Long> {
}
