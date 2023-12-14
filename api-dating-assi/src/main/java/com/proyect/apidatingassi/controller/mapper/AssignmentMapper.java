package com.proyect.apidatingassi.controller.mapper;

import com.proyect.apidatingassi.controller.dto.AssignmentDto;
import com.proyect.apidatingassi.model.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);


    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "descripcion", source = "description", defaultValue = "")
    @Mapping(target = "precio", source = "price")
    AssignmentDto toDto(Assignment assignment);
}
