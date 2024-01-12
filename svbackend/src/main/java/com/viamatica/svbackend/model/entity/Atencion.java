package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.viamatica.svbackend.util.enums.AttentionStatus;
import com.viamatica.svbackend.util.enums.AttentionType;
import com.viamatica.svbackend.util.serializers.ClienteConPagoSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "atenciones")
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atencion_id")
    private Long id;
    @Column(name = "tipo_atencion")
    @Enumerated(EnumType.STRING)
    private AttentionType attentionType;
    private String descripcion;
    @Column(name = "estado_atencion")
    @ColumnDefault("'NUEVO'")
    @Enumerated(EnumType.STRING)
    private AttentionStatus attentionStatus;

    // cada atención entra a una sola caja
    @ManyToOne
    @JoinColumn(name="caja_id", referencedColumnName = "caja_id")
    @JsonBackReference
    private Caja caja;

    // una atención es efectuada a un mismo cliente
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cliente_id", referencedColumnName = "cliente_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonSerialize(using = ClienteConPagoSerializer.class)
    private Cliente cliente;

    public void setDescripcion(){
        this.descripcion = attentionType.getDescription();
    }

}
