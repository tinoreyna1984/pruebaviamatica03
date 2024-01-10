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

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public GenericResponse<?> login(AuthenticationRequest authRequest) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authToken);

        Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());
        if(optionalUser.isEmpty()){
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el usuario.",
                            null);
        }
        User user = optionalUser.get();

        if(user.getUserStatus() == UserStatus.NOT_APPROVED){
            return GenericResponse
                    .getResponse(403,
                            "Necesita aprobación del administrador o gestor para acceder al sistema",
                            null);
        }

        try{
            String jwt = jwtService.generateToken(user, generateExtraClaims(user));
            return GenericResponse.getResponse(200, "Se dio acceso al usuario", jwt);
        }catch (DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al dar acceso: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }catch (AuthenticationException e) {
            return GenericResponse
                    .getResponse(401,
                            "Error en las credenciales: " + e.getMessage(),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", user.getUsername());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());
        extraClaims.put("routes", user.getRole().getRoutes());

        return extraClaims;
    }

}
