package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Date fechaPago;

    // varios pagos pueden proceder del mismo cliente
    @ManyToOne
    @JoinColumn(name="cliente_id", referencedColumnName = "cliente_id")
    @JsonBackReference
    private Cliente cliente;
}
