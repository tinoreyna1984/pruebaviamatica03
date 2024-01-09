package com.viamatica.svbackend.model.entity;

import com.viamatica.svbackend.util.ContractStatus;
import com.viamatica.svbackend.util.PaymentMethod;
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
    private Date fechaInicioContrato;
    @Column(name = "fecha_fin_contrato")
    private Date fechaFinContrato;
    @Column(name = "estado_contrato")
    @ColumnDefault("'VIG'")
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;
    @Column(name = "forma_pago")
    @ColumnDefault("'EFECTIVO'")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}
