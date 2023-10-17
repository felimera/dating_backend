package com.proyect.apidatingappus.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_id")
    private Long id;
    @Column(name = "us_name")
    private String name;
    @Column(name = "us_email")
    private String email;
    @Column(name = "us_password")
    private String password;
}
