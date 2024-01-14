package com.viamatica.svbackend.model.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CajaRequest {
    @Pattern(regexp = "\\bCAJA\\d{4}\\b", message = "El nombre de caja debe componerse de la palabra CAJA con 4 dígitos.")
    private String descripcion;
    private boolean active = true;
}
