package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.RouterLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouterLinkRepository extends JpaRepository<RouterLink, Long> {
}
