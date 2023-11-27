package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.model.User;

public interface UserService {

    User putUser(Long id, User user);

    User findById(Long idUser);

    boolean isExistUser(String email);
}
