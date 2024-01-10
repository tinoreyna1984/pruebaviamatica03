package com.viamatica.svbackend.controller;

import com.viamatica.svbackend.model.dto.request.ClienteRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/clientes")
    // devuelve una lista completa o paginada si viajan parámetros de paginación
    public ResponseEntity<GenericResponse<?>> listarClientes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(clienteService.getClientes(page, size)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<GenericResponse<Cliente>> buscarCliente(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(clienteService.getCliente(id)
                );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PostMapping("/clientes")
    public ResponseEntity<GenericResponse<?>> guardarCliente(@Valid @RequestBody ClienteRequest clienteRequest, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(clienteService.saveCliente(clienteRequest, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<GenericResponse<?>> editarCliente(@Valid @RequestBody ClienteRequest clienteRequest, @PathVariable Long id, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(clienteService.updateCliente(clienteRequest, id, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<GenericResponse<?>> borrarCliente(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(clienteService.deleteCliente(id)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER','ROLE_USER')")
    @PostMapping(value = "/clientes/csv", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GenericResponse<?>> cargarDesdeCSV(@RequestPart(value = "archivo") MultipartFile archivo) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(clienteService.cargarDesdeCSV(archivo)
                );
    }

}
