package com.viamatica.svbackend.service;

import com.viamatica.svbackend.model.dto.request.AuthenticationRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.User;
import com.viamatica.svbackend.repository.UserRepository;
import com.viamatica.svbackend.util.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public GenericResponse<?> login(AuthenticationRequest authRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());
        if(optionalUser.isEmpty()){
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el usuario.",
                            null);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            return GenericResponse
                    .getResponse(403,
                            "La contraseña es incorrecta.",
                            null);
        }catch (DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al dar acceso: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        User user = optionalUser.get();
        if(user.getUserStatus() == UserStatus.NOT_APPROVED){
            return GenericResponse
                    .getResponse(403,
                            "Necesita aprobación del administrador o gestor para acceder al sistema",
                            null);
        }

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return GenericResponse.getResponse(200, "Se dio acceso al usuario", jwt);

    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", user.getUsername());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());
        extraClaims.put("routes",
                user.getRole()
                        .getRoutes().stream()
                        .map(route -> Map.of("name", route.getName(), "path", route.getPath()))
                        .collect(Collectors.toList())); // rutas

        return extraClaims;
    }

}
