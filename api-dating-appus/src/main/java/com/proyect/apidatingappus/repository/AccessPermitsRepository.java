package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.AccessPermits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessPermitsRepository extends JpaRepository<AccessPermits, Long>, AccessPermitsCriteriaRepository {
}
