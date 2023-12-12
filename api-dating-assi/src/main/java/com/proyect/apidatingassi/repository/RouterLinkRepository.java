package com.proyect.apidatingassi.repository;

import com.proyect.apidatingassi.model.RouterLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouterLinkRepository extends JpaRepository<RouterLink, Long> {
    @Query("select rl from RouterLink rl where rl.id = 1 ")
    Optional<RouterLink> getRouterLinkByOne();
}
