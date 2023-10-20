package com.proyect.apidatingappus.repository;

import com.proyect.apidatingappus.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findOneByEmail(String email);
}
