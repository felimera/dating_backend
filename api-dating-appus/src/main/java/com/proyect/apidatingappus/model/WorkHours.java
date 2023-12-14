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
@Table(name = "work_hours")
public class WorkHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wh_id")
    private Long id;
    @Column(name = "wh_value")
    private String value;
    @Column(name = "wh_disabled")
    private Boolean displased;
}
