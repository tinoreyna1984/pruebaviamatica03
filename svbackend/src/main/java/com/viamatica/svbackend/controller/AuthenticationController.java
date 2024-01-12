package com.viamatica.svbackend.controller;

import com.viamatica.svbackend.model.dto.request.AuthenticationRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<GenericResponse<?>> login(@RequestBody AuthenticationRequest authRequest){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.login(authRequest)
                );
    }

    @PreAuthorize("permitAll")
    @GetMapping("/public-access")
    public String publicAccessEndpoint(){
        return "Este endpoint es público";
    }

}
