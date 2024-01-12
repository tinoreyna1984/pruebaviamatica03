package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.viamatica.svbackend.util.serializers.DispositivoSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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
    @JsonSerialize(using = DispositivoSerializer.class) // prueba
    private Dispositivo dispositivo;

    // un servicio tiene varios contratos
    @OneToMany(mappedBy = "servicio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonIgnore
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    //@JsonManagedReference
    private Set<Contrato> contratos;
}
