package com.proyect.apidatingappus.controller.mapper;

import com.proyect.apidatingappus.controller.dto.RouterLinkDto;
import com.proyect.apidatingappus.model.RouterLink;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RouterLinkMapper {

    RouterLinkMapper INSTANCE = Mappers.getMapper(RouterLinkMapper.class);

    RouterLinkDto toDto(RouterLink entity);
}
