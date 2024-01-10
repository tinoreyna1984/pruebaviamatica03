package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicio_id")
    private Long id;
    private String descripcion;
    private Double precio;

    // un servicio tiene un dispositivo
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dispositivo_id", referencedColumnName = "dispositivo_id")
    private Dispositivo dispositivo;

    // un servicio tiene varios contratos
    @OneToMany(mappedBy = "servicio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private List<Contrato> contratos;
}
