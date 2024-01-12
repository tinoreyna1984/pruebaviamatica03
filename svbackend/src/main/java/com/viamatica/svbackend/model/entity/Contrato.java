package com.viamatica.svbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.viamatica.svbackend.util.enums.ContractStatus;
import com.viamatica.svbackend.util.enums.PaymentMethod;
import com.viamatica.svbackend.util.serializers.ServicioSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contrato_id")
    private Long id;
    @Column(name = "fecha_inicio_contrato")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaInicioContrato;
    @Column(name = "fecha_fin_contrato")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaFinContrato;
    @Column(name = "estado_contrato")
    @ColumnDefault("'VIG'")
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;
    @Column(name = "forma_pago")
    @ColumnDefault("'EFECTIVO'")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // cada contrato es para un servicio
    @ManyToOne
    @JoinColumn(name="servicio_id", referencedColumnName = "servicio_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonSerialize(using = ServicioSerializer.class) // prueba
    //@JsonBackReference
    private Servicio servicio;

    // cada contrato es de un cliente
    @ManyToOne
    @JoinColumn(name="cliente_id", referencedColumnName = "cliente_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Cliente cliente;
}
