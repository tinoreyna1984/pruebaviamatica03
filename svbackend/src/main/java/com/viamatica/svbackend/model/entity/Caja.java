package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cajas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caja_id")
    private Long id;
    private String descripcion;
    private boolean active = true;

    // en una caja trabajan varios usuarios
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_caja",
            joinColumns = @JoinColumn(name = "caja_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    Set<User> users;
    @PreRemove // borra en cascada
    private void removeUsersFromCaja() {
        for (User user : users) {
            user.getCajas().remove(this);
        }
        users.clear();
    }

    // en una caja hay varias atenciones
    @OneToMany(mappedBy = "caja", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private Set<Atencion> atenciones;
}
