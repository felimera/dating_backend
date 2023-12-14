package com.project.apidatingjobs.model;

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
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;
    @Column(name = "app_appo_date")
    private LocalDate date;
    @Column(name = "app_appo_time")
    private String time;
    @Column(name = "app_total_price")
    private Long totalPrice;
    @Column(name = "app_valid")
    private String valid;
}
