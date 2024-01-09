package com.viamatica.svbackend.util;

import lombok.Getter;

@Getter
public enum Route {
    HOME("Inicio",""),
    CUSTOMERS("Clientes","customers"),
    USERS("Usuarios","users"),
    ATTENTIONS("Atenciones", "attentions"),
    CONTRACTS("Contratos", "contracts")
    ;

    private String name;
    private String path;

    Route(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
