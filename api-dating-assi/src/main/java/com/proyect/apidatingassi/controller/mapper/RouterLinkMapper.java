package com.proyect.apidatingassi.controller.mapper;

import com.proyect.apidatingassi.controller.dto.RouterLinkDto;
import com.proyect.apidatingassi.model.RouterLink;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RouterLinkMapper {

    RouterLinkMapper INSTANCE = Mappers.getMapper(RouterLinkMapper.class);

    RouterLinkDto toDto(RouterLink entity);
}
