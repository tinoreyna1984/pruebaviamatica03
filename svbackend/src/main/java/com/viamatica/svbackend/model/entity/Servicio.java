package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonIgnore
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private List<Contrato> contratos;
}
