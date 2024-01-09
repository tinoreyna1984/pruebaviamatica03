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
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atencion_id")
    private Long id;
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private AttentionType attentionType;
    @ColumnDefault("'NUEVO'")
    @Enumerated(EnumType.STRING)
    private AttentionStatus attentionStatus;

    public String getDescripcion() {
        return attentionType.getDescription();
    }
}
