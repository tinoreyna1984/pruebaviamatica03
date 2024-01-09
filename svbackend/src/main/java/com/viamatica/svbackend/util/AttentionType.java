package com.viamatica.svbackend.util;

import lombok.Getter;

@Getter
public enum AttentionType {
    AC0001("AC0001 - Contrato de servicio"), // Contrato de servicio
    AC0002("AC0002 - Cambio de servicio"), // Cambio de servicio
    AC0003("AC0003 - Cancelación de servicio"), // Cancelación de servicio
    PS0001("PS0001 - Pago de servicios"), // Pago de servicios
    PS0002("PS0002 - Cambio de forma de pago"), // Cambio de forma de pago
    ;

    private final String description;

    AttentionType(String description) {
        this.description = description;
    }

}
