package com.viamatica.svbackend.controller;

import com.viamatica.svbackend.model.dto.request.UserRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.User;
import com.viamatica.svbackend.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @GetMapping("/users")
    // devuelve una lista completa o paginada si viajan parámetros de paginación
    public ResponseEntity<GenericResponse<?>> listarUsuarios(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.get(page, size)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<GenericResponse<User>> buscarUsuario(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.getById(id)
                );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @PostMapping("/users")
    public ResponseEntity<GenericResponse<?>> guardarUsuario(@Valid @RequestBody UserRequest userRequest, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.save(userRequest, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @PutMapping("/users/{id}")
    public ResponseEntity<GenericResponse<?>> editarUsuario(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id, BindingResult result){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.update(userRequest, id, result)
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<GenericResponse<?>> borrarUsuario(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.delete(id)
                );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @GetMapping("/dashboard")
    public ResponseEntity<GenericResponse<?>> getDashboard(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.dashboard()
                );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_MANAGER')")
    @PostMapping(value = "/users/csv", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GenericResponse<?>> cargarDesdeCSV(@RequestPart(value = "archivo") MultipartFile archivo) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom-status", "OK")
                .body(userService.cargarDesdeCSV(archivo)
                );
    }

}
