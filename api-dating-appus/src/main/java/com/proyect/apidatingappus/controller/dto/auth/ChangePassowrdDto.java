package com.proyect.apidatingappus.controller.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassowrdDto {
    private String email;
    private String passwordOld;
    private String passwordNew;
}
