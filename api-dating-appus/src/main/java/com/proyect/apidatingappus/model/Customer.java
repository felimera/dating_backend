package com.proyect.apidatingappus.model;

import com.proyect.apidatingappus.model.complement.Gender;
import com.proyect.apidatingappus.model.complement.Rol;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cus_id")
    private Long id;
    @Column(name = "cus_firt_name")
    private String firtName;
    @Column(name = "cus_last_name")
    private String lastName;
    @Column(name = "cus_phone")
    @Nullable
    private String phone;
    @Column(name = "cus_email")
    private String email;
    @Column(name = "cus_rol")
    @Enumerated(value = EnumType.STRING)
    private Rol rol;
    @Column(name = "cus_gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column(name = "cus_birthdate")
    private LocalDate birthdate;
}
