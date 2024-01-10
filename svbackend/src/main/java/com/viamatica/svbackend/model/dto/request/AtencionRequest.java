package com.viamatica.svbackend.model.dto.request;

import com.viamatica.svbackend.util.enums.AttentionStatus;
import com.viamatica.svbackend.util.enums.AttentionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtencionRequest {
    @Enumerated(EnumType.STRING)
    @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "La descripción del turno debe tener 6 caracteres y " +
            "compuesta por 2 letras mayúsculas y 4 números.")
    private AttentionType attentionType;
    @Enumerated(EnumType.STRING)
    private AttentionStatus attentionStatus;
    private Long cajaId;
    private Long clienteId;
}
