package com.viamatica.svbackend.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCajaRequest {
    private Long userId;
    private Long cajaId;
}
