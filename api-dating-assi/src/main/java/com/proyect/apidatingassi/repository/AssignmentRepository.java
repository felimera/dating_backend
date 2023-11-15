package com.proyect.apidatingassi.repository;

import com.proyect.apidatingassi.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Query("select a from Assignment a where a.id in :ids")
    List<Assignment> getByListIds(List<Long> ids);
}
