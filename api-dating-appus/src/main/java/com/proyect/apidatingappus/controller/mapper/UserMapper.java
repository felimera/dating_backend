package com.proyect.apidatingappus.controller.mapper;

import com.proyect.apidatingappus.controller.dto.UserDto;
import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.util.Constants;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);

    @Mapping(target = "name", expression = "java(getNombres(signUpDto))")
    User toSignUp(SignUpDto signUpDto);

    default String getNombres(SignUpDto signUpDto) {
        return signUpDto.getNombres().replace(Constants.AMPERSAND, Constants.SPACE);
    }
}
