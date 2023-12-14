package com.proyect.apidatingappus.controller.mapper;

import com.proyect.apidatingappus.controller.dto.WorkHoursDto;
import com.proyect.apidatingappus.model.WorkHours;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkHoursMapper {
    WorkHoursMapper INSTANCE = Mappers.getMapper(WorkHoursMapper.class);

    WorkHoursDto toDto(WorkHours entity);
}
