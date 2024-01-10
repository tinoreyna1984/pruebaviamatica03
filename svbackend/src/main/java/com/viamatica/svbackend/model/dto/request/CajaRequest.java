package com.viamatica.svbackend.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CajaRequest {
    private String descripcion;
    private boolean active = true;
}
