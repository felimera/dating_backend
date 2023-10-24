package com.proyect.apidatingappus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private Long price;
}
