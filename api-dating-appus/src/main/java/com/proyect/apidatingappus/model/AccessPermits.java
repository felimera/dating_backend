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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ap_router_link_id")
    private RouterLink routerLink;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ap_tipo_role_id")
    private TipoRole tipoRole;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ap_user_id")
    private User user;
}
