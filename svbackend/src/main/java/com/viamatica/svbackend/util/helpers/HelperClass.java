package com.viamatica.svbackend.util.helpers;

import com.viamatica.svbackend.model.dto.request.UserRequest;
import com.viamatica.svbackend.model.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.List;

public class HelperClass {

    // codificador de password
    private final PasswordEncoder passwordEncoder;

    public HelperClass() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String validaRequest(BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorsList = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            StringBuilder errors = new StringBuilder();
            for (String error : errorsList) {
                errors.append(error).append(" ");
            }
            return errors.toString();
        }
        return "";
    }

    public void encriptarClaveUserRequest(UserRequest userRequest) {
        String claveEncriptada = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(claveEncriptada);
    }
    public void encriptarClaveUsuario(User user) {
        String claveEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(claveEncriptada);
    }
}
