package com.viamatica.svbackend.model.entity;

import com.viamatica.svbackend.util.AttentionStatus;
import com.viamatica.svbackend.util.AttentionType;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atencion")
    private AttentionType attentionType;
    private String descripcion;
    @ColumnDefault("'NUEVO'")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_atencion")
    private AttentionStatus attentionStatus;

    public String getDescripcion() {
        return attentionType.getDescription();
    }
}
