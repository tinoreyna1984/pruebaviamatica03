package com.viamatica.svbackend.controller;

import com.viamatica.svbackend.model.dto.request.ContratoRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Contrato;
import com.viamatica.svbackend.service.crud.impl.ContratoService;
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
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/contratos")
    // devuelve una lista completa o paginada si viajan parámetros de paginación
    public ResponseEntity<GenericResponse<?>> listarContratos(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(contratoService.get(page, size)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/contratos/{id}")
    public ResponseEntity<GenericResponse<Contrato>> buscarContrato(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(contratoService.getById(id)
                );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PostMapping("/contratos")
    public ResponseEntity<GenericResponse<?>> guardarContrato(@Valid @RequestBody ContratoRequest request, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(contratoService.save(request, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PutMapping("/contratos/{id}")
    public ResponseEntity<GenericResponse<?>> editarContrato(@Valid @RequestBody ContratoRequest request, @PathVariable Long id, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(contratoService.update(request, id, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @DeleteMapping("/contratos/{id}")
    public ResponseEntity<GenericResponse<?>> borrarContrato(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .contentType(MediaType.APPLICATION_JSON)
                .body(contratoService.delete(id)
                );
    }

}
