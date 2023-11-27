package com.proyect.apidatingappus.controller.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassowrdDto {
    private String email;
    private String passwordOld;
    private String passwordNew;
}
