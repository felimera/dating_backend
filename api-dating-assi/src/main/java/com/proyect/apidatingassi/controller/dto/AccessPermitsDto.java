package com.proyect.apidatingassi.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccessPermitsDto {
    private Long id;
    private List<RouterLinkDto> listRouterList;
    private String tipoRoleAcronym;
}
