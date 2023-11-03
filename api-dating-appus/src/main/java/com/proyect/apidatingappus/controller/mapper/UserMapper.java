package com.proyect.apidatingappus.controller.mapper;

import com.proyect.apidatingappus.controller.dto.UserDto;
import com.proyect.apidatingappus.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);
}
