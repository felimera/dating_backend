package com.proyect.apidatingappus.model.entitytest;

import com.proyect.apidatingappus.controller.dto.auth.ChangePassowrdDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ChangePassowrdDtoBuilder {
    private String email;
    private String passwordOld;
    private String passwordNew;

    private ChangePassowrdDtoBuilder toChangePassowrdDtoBuilder() {
        return ChangePassowrdDtoBuilder
                .builder()
                .email("test@test.com")
                .passwordOld("password")
                .passwordNew("test_123456")
                .build();
    }

    public ChangePassowrdDto toChangePassowrdDto() {
        ChangePassowrdDtoBuilder builder = toChangePassowrdDtoBuilder();
        return new ChangePassowrdDto(builder.email, builder.passwordOld, builder.passwordNew);
    }
}
