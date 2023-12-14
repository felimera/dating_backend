package com.proyect.apidatingappus.model.complement;

import java.util.stream.Stream;

public enum Rol {
    A("Administracion"), U("Usuario");
    private String role;

    private Rol(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Rol of(String rol) {
        return Stream.of(Rol.values())
                .filter(p -> p.getRole().equals(rol))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
