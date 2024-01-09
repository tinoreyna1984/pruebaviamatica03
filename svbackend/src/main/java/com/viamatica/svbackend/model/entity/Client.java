package com.viamatica.svbackend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;
    private String name;
    private String lastName;
    private String docId;
    private String email;
    private String phone;
    private String address;
    private String refAddress;
}
