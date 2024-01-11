package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dispositivos")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispositivo_id")
    private Long id;
    private String nombre;

    // un dispositivo pertenece a un servicio
    @JsonIgnore
    @OneToOne(mappedBy = "dispositivo")
    private Servicio servicio;
}
