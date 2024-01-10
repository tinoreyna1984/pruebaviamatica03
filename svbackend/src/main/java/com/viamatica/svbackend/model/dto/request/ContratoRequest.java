package com.viamatica.svbackend.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viamatica.svbackend.util.enums.ContractStatus;
import com.viamatica.svbackend.util.enums.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
public class ContratoRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaInicioContrato;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaFinContrato;
    @ColumnDefault("'VIG'")
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;
    @ColumnDefault("'EFECTIVO'")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Long servicioId;
    private Long clienteId;
}
