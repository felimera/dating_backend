package com.proyect.apidatingappus.controller.mapper;

import com.proyect.apidatingappus.controller.dto.CustomerDto;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.util.Constants;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "nombre", source = "firtName")
    @Mapping(target = "apellido", source = "lastName")
    @Mapping(target = "telefono", source = "phone")
    @Mapping(target = "correo", source = "email")
    @Mapping(target = "genero", expression = "java(getGender(customer))")
    @Mapping(target = "fechaNacimiento", source = "birthdate", dateFormat = Constants.DATE_FORMAT)
    @Mapping(target = "rol", expression = "java(getRol(customer))")
    CustomerDto toDto(Customer customer);

    @InheritInverseConfiguration
    @Mapping(target = "gender", source = "genero")
    Customer toEntity(CustomerDto customerDto);

    default String getGender(Customer customer) {
        return Objects.nonNull(customer.getGender()) && Objects.nonNull(customer.getGender().getName()) ? customer.getGender().getName() : "";
    }

    default String getRol(Customer customer) {
        return Objects.nonNull(customer.getRol()) && Objects.nonNull(customer.getRol().getRole()) ? customer.getRol().getRole() : "";
    }
}
