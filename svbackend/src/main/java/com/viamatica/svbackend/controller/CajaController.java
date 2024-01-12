package com.viamatica.svbackend.controller;

import com.viamatica.svbackend.model.dto.request.CajaRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Caja;
import com.viamatica.svbackend.service.crud.impl.CajaService;
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
public class CajaController {
    @Autowired
    private CajaService cajaService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/cajas")
    // devuelve una lista completa o paginada si viajan parámetros de paginación
    public ResponseEntity<GenericResponse<?>> listarAtenciones(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(cajaService.get(page, size)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/cajas/{id}")
    public ResponseEntity<GenericResponse<Caja>> buscarCaja(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(cajaService.getById(id)
                );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PostMapping("/cajas")
    public ResponseEntity<GenericResponse<?>> guardarCaja(@Valid @RequestBody CajaRequest request, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(cajaService.save(request, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PutMapping("/cajas/{id}")
    public ResponseEntity<GenericResponse<?>> editarCaja(@Valid @RequestBody CajaRequest request, @PathVariable Long id, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(cajaService.update(request, id, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @DeleteMapping("/cajas/{id}")
    public ResponseEntity<GenericResponse<?>> borrarCaja(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(cajaService.delete(id)
                );
    }

}
