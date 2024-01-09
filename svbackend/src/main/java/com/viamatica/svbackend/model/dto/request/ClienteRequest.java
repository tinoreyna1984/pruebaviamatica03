package com.viamatica.svbackend.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
    private String name;
    private String lastName;
    private String docId;
    private String email;
    private String phone;
    private String address;
    private String refAddress;
}
