package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.model.User;

public interface UserService {

    User putUser(User user);

    User findById(Long idUser);

    boolean isExistUser(String email);
}
