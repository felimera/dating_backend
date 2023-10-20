package com.proyect.apidatingappus.model.complement;

import java.util.stream.Stream;

public enum Gender {
    M("Masculino"), F("Femenino");
    private String name;

    private Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Gender of(String gender) {
        return Stream.of(Gender.values())
                .filter(p -> p.getName().equals(gender))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
