package com.proyect.apidatingappus.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_assignment_id")
    private Assignment assignment;
}
