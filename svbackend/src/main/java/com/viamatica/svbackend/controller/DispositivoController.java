package com.viamatica.svbackend.controller;

import com.viamatica.svbackend.model.dto.request.DispositivoRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Dispositivo;
import com.viamatica.svbackend.service.crud.impl.DispositivoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/dispositivos")
    // devuelve una lista completa o paginada si viajan parámetros de paginación
    public ResponseEntity<GenericResponse<?>> listarDispositivos(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dispositivoService.get(page, size)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/dispositivos/{id}")
    public ResponseEntity<GenericResponse<Dispositivo>> buscarDispositivo(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dispositivoService.getById(id)
                );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PostMapping("/dispositivos")
    public ResponseEntity<GenericResponse<?>> guardarDispositivo(@Valid @RequestBody DispositivoRequest request, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dispositivoService.save(request, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PutMapping("/dispositivos/{id}")
    public ResponseEntity<GenericResponse<?>> editarDispositivo(@Valid @RequestBody DispositivoRequest request, @PathVariable Long id, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dispositivoService.update(request, id, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @DeleteMapping("/dispositivos/{id}")
    public ResponseEntity<GenericResponse<?>> borrarDispositivo(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dispositivoService.delete(id)
                );
    }

}
