package com.proyect.apidatingappus.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ass_id")
    private Long id;
    @Column(name = "ass_name")
    private String name;
    @Column(name = "ass_description")
    private String description;
    @Column(name = "ass_price")
    private long price;
}
