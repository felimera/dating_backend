package com.proyect.apidatingappus.model.entitytest;

import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.util.Constants;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class SignUpDtoBuider {
    private String nombres;
    private String email;
    private String password;
    private String fechaNacimiento;
    private String genero;
    private String rol;
    private String telefono;

    private SignUpDtoBuider toSignUpDtoBuider() {
        return SignUpDtoBuider.builder()
                .nombres("Julian&Gomez")
                .email("julian@julian.com")
                .password("password")
                .fechaNacimiento(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)))
                .genero("M")
                .rol("U")
                .telefono("231457852")
                .build();
    }

    public SignUpDto toSignUpDto() {
        SignUpDtoBuider builder = toSignUpDtoBuider();
        return new SignUpDto(builder.nombres, builder.email, builder.password, builder.fechaNacimiento, builder.genero, builder.rol, builder.telefono);
    }

    public SignUpDto toEditPassword(String password) {
        SignUpDtoBuider builder = toSignUpDtoBuider();
        return new SignUpDto(builder.nombres, builder.email, password, builder.fechaNacimiento, builder.genero, builder.rol, builder.telefono);
    }
}
