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
@Table(name = "access_permits")
public class AccessPermits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ap_id")
    private Long id;
    @Column(name = "ap_label")
    private String label;
    @Column(name = "ap_icon")
    private String icon;
    @Column(name = "ap_url")
    private String url;
    @Column(name = "ap_description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ap_tipo_role_id")
    private TipoRole tipoRole;
}
