package com.proyect.apidatingappus.model.entitytest;

import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.complement.Gender;
import com.proyect.apidatingappus.model.complement.Rol;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerBuilder {

    private Long id;
    private String firtName;
    private String lastName;
    private String phone;
    private String email;
    private Rol rol;
    private Gender gender;
    private LocalDate birthdate;

    private CustomerBuilder toCustomerBuilder() {
        return CustomerBuilder.builder()
                .id(1L)
                .firtName("Julio")
                .lastName("Agosto")
                .phone("321456897")
                .email("julio@julio.com")
                .rol(Rol.U)
                .gender(Gender.M)
                .birthdate(LocalDate.now().minusYears(18))
                .build();
    }

    public Customer toCustomer() {
        CustomerBuilder builder = toCustomerBuilder();
        return new Customer(builder.id, builder.firtName, builder.lastName, builder.phone, builder.email, builder.rol, builder.gender, builder.birthdate);
    }
}
