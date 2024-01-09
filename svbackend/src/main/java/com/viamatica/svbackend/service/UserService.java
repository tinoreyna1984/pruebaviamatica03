package com.viamatica.svbackend.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.viamatica.svbackend.model.dto.request.UserRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.User;
import com.viamatica.svbackend.repository.UserRepository;
import com.viamatica.svbackend.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // codificador de password
    @Autowired
    private PasswordEncoder passwordEncoder;

    private void encriptarClaveUserRequest(UserRequest userRequest) {
        String claveEncriptada = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(claveEncriptada);
    }

    private void encriptarClaveUsuario(User user) {
        String claveEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(claveEncriptada);
    }

    public GenericResponse<?> getUsers(Integer page, Integer size){
        Map<String, Object> response = new HashMap<>();
        try{
            if (page != null && size != null) {
                // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
                Pageable pageable = PageRequest.of(page, size);
                Page<User> pageResult = userRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los registros", pageResult);
            } else {
                // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
                List<User> users = userRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los registros", users);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar usuarios: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
    }

    public GenericResponse<User> getUser(Long id){
        User usuario = null;
        try {
            usuario = userRepository.findById(id).get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar usuario: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
        return GenericResponse.getResponse(200, "Registro encontrado", usuario);
    }

    public GenericResponse<?> saveUser(UserRequest userRequest, BindingResult result){
        User usuarioNuevo = new User();

        // si no viaja el ROL, por defecto debe ser el de USUARIO
        if(userRequest.getRole() == null)
            userRequest.setRole(Role.USER);

        // proceso de validación
        String errors = validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear usuario", errors);

        // encripta clave
        encriptarClaveUserRequest(userRequest);

        usuarioNuevo.setEmail(userRequest.getEmail());
        usuarioNuevo.setUsername(userRequest.getUsername());
        usuarioNuevo.setPassword(userRequest.getPassword());
        usuarioNuevo.setRole(userRequest.getRole());

        try {
            usuarioNuevo = userRepository.save(usuarioNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear usuario: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }

        return GenericResponse.getResponse(201, "Registro creado", usuarioNuevo);
    }

    public GenericResponse<?> updateUser(UserRequest userRequest, Long id, BindingResult result){
        // proceso de validación
        String errors = validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al actualizar usuario", errors);

        User usuarioActual = userRepository.findById(id).get();
        User usuarioEditado = null;
        try {
            usuarioActual.setEmail(userRequest.getEmail());
            usuarioActual.setUsername(userRequest.getUsername());
            usuarioActual.setPassword(userRequest.getPassword());
            // si no viaja el ROL, por defecto debe ser el de USUARIO
            if(userRequest.getRole() == null)
                usuarioActual.setRole(Role.USER);
            else
                usuarioActual.setRole(userRequest.getRole());
            // encripta clave
            encriptarClaveUsuario(usuarioActual);
            usuarioEditado = userRepository.save(usuarioActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar usuario: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
        return GenericResponse.getResponse(200, "Registro actualizado", usuarioEditado);
    }

    public GenericResponse<?> deleteUser(Long id){
        try {
            userRepository.deleteById(id);
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al realizar la consulta en la base de datos: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }

        return GenericResponse.getResponse(200, "Registro borrado", null);
    }

    public GenericResponse<?> dashboard(){
        Map<String, Object> dashboard = new HashMap<>();
        long totalUsers = 0L;
        long activeUsers = 0L;
        long lockedUsers = 0L;
        try {
            /*totalUsers = userRepository.totalUsers();
            activeUsers = userRepository.totalActiveUsers();
            lockedUsers = userRepository.totalLockedUsers();*/
            dashboard.put("total", totalUsers);
            dashboard.put("activos", activeUsers);
            dashboard.put("bloqueados", lockedUsers);
            return GenericResponse.getResponse(200, "Registro encontrado", dashboard);
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al obtener valores del dashboard: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
    }

    public GenericResponse<?> cargarDesdeCSV(MultipartFile archivo) throws IOException {
        try (
             Reader reader = new InputStreamReader(archivo.getInputStream());
             CSVReader csvReader = new CSVReaderBuilder(reader).build()
        ) {
            List<String[]> filas = csvReader.readAll();

            for (String[] fila : filas) {
                UserRequest userRequest = pasarValores(fila);
                User user = new User();
                user.setUsername(userRequest.getUsername());
                encriptarClaveUserRequest(userRequest);
                user.setPassword(userRequest.getPassword());
                user.setEmail(userRequest.getEmail());
                user.setRole(userRequest.getRole());
                userRepository.save(user);
            }
            return GenericResponse.getResponse(201, "Se creó registro desde CSV", archivo.getName());
        } catch (CsvException e) {
            return GenericResponse
                    .getResponse(400,
                            "Error al cargar desde CSV, revisar archivo ",
                            null);
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al cargar desde CSV: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
    }

    private UserRequest pasarValores(String[] fila) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(fila[0]);
        userRequest.setPassword(fila[1]);
        userRequest.setEmail(fila[2]);
        userRequest.setRole(Role.valueOf(fila[3]));
        return userRequest;
    }


    private String validaRequest(BindingResult result) {
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

}
