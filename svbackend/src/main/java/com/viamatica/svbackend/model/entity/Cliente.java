package com.viamatica.svbackend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "doc_id")
    private String docId;
    private String email;
    @Column(name = "fono")
    private String phone;
    @Column(name = "direccion")
    private String address;
    @Column(name = "referencia_dir")
    private String refAddress;
}
