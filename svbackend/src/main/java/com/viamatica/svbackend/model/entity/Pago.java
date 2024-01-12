package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.viamatica.svbackend.util.serializers.ClienteSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long id;
    @Column(name = "fecha_pago")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaPago;

    // varios pagos pueden proceder del mismo cliente
    @ManyToOne
    @JoinColumn(name="cliente_id", referencedColumnName = "cliente_id")
    //@JsonBackReference
    //@JsonIgnore
    @JsonSerialize(using = ClienteSerializer.class)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Cliente cliente;
}
