package com.proyect.apidatingappus.controller.mapper;

import com.proyect.apidatingappus.controller.dto.AppointmentDto;
import com.proyect.apidatingappus.model.Appointment;
import com.proyect.apidatingappus.util.Constants;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(target = "fecha", source = "date", dateFormat = Constants.DATE_FORMAT)
    @Mapping(target = "hora", source = "time")
    @Mapping(target = "precioTotal", source = "totalPrice")
    @Mapping(target = "idCustomer", source = "customer.id")
    @Mapping(target = "idAssignment", source = "assignment.id")
    AppointmentDto toDto(Appointment appointment);

    @InheritInverseConfiguration
    Appointment toEntity(AppointmentDto appointmentDto);
}