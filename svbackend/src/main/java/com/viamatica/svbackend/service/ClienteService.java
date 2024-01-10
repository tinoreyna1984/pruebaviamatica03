package com.viamatica.svbackend.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.viamatica.svbackend.model.dto.request.ClienteRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.repository.ClienteRepository;
import com.viamatica.svbackend.util.helpers.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    private final HelperClass helperClass = new HelperClass();

    public GenericResponse<?> getClientes(Integer page, Integer size){
        try{
            if (page != null && size != null) {
                // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
                Pageable pageable = PageRequest.of(page, size);
                Page<Cliente> pageResult = clienteRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los clientes", pageResult);
            } else {
                // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
                List<Cliente> users = clienteRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los clientes", users);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar clientes: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
    }

    public GenericResponse<Cliente> getCliente(Long id){
        Cliente cliente = null;
        try {
            cliente = clienteRepository.findById(id).get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar cliente: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
        return GenericResponse.getResponse(200, "Cliente encontrado", cliente);
    }

    public GenericResponse<?> saveCliente(ClienteRequest clienteRequest, BindingResult result){
        Cliente clienteNuevo = new Cliente();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear usuario", errors);

        clienteNuevo.setName(clienteRequest.getName());
        clienteNuevo.setLastName(clienteRequest.getLastName());
        clienteNuevo.setDocId(clienteRequest.getDocId());
        clienteNuevo.setEmail(clienteRequest.getEmail());
        clienteNuevo.setPhone(clienteRequest.getPhone());
        clienteNuevo.setAddress(clienteRequest.getAddress());
        clienteNuevo.setRefAddress(clienteRequest.getRefAddress());

        try {
            clienteNuevo = clienteRepository.save(clienteNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear cliente: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }

        return GenericResponse.getResponse(201, "Cliente creado", clienteNuevo);
    }

    public GenericResponse<?> updateCliente(ClienteRequest clienteRequest, Long id, BindingResult result){
        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al actualizar usuario", errors);

        Cliente clienteActual = clienteRepository.findById(id).get();
        Cliente clienteEditado = null;
        try {
            clienteActual.setName(clienteRequest.getName());
            clienteActual.setLastName(clienteRequest.getLastName());
            clienteActual.setDocId(clienteRequest.getDocId());
            clienteActual.setEmail(clienteRequest.getEmail());
            clienteActual.setPhone(clienteRequest.getPhone());
            clienteActual.setAddress(clienteRequest.getAddress());
            clienteActual.setRefAddress(clienteRequest.getRefAddress());
            clienteEditado = clienteRepository.save(clienteActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar cliente: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
        return GenericResponse.getResponse(200, "Cliente actualizado", clienteEditado);
    }

    public GenericResponse<?> deleteCliente(Long id){
        try {
            clienteRepository.deleteById(id);
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al realizar la consulta en la base de datos: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }

        return GenericResponse.getResponse(200, "Cliente borrado", null);
    }

    public GenericResponse<?> cargarDesdeCSV(MultipartFile archivo) throws IOException {
        try (
                Reader reader = new InputStreamReader(archivo.getInputStream());
                CSVReader csvReader = new CSVReaderBuilder(reader).build()
        ) {
            List<String[]> filas = csvReader.readAll();

            for (String[] fila : filas) {
                ClienteRequest clienteRequest = pasarValores(fila);
                Cliente cliente = new Cliente();
                cliente.setName(clienteRequest.getName());
                cliente.setLastName(clienteRequest.getLastName());
                cliente.setDocId(clienteRequest.getDocId());
                cliente.setEmail(clienteRequest.getEmail());
                cliente.setPhone(clienteRequest.getPhone());
                cliente.setAddress(clienteRequest.getAddress());
                cliente.setRefAddress(clienteRequest.getRefAddress());
                clienteRepository.save(cliente);
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

    private ClienteRequest pasarValores(String[] fila) {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setName(fila[0]);
        clienteRequest.setLastName(fila[1]);
        clienteRequest.setDocId(fila[2]);
        clienteRequest.setEmail(fila[3]);
        clienteRequest.setPhone(fila[4]);
        clienteRequest.setAddress(fila[5]);
        clienteRequest.setRefAddress(fila[6]);
        return clienteRequest;
    }

}
