package com.viamatica.svbackend.service.crud.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.viamatica.svbackend.model.dto.request.ClienteRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.repository.ClienteRepository;
import com.viamatica.svbackend.service.crud.GenericService;
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
import java.util.Optional;

@Service
public class ClienteService implements GenericService<Cliente, ClienteRequest> {

    @Autowired
    private ClienteRepository clienteRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size){
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
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Cliente> getById(Long id){
        Cliente cliente;
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        try {
            if(optionalCliente.isEmpty()){
                return GenericResponse
                        .getResponse(400,
                                "No se encuentra el cliente con ID " + id,
                                null);
            }
            cliente = optionalCliente.get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar cliente: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Cliente encontrado", cliente);
    }

    @Override
    public GenericResponse<?> save(ClienteRequest request, BindingResult result){

        // valida que no exista cliente
        Optional<Cliente> optionalCliente = clienteRepository.findByDocId(request.getDocId());
        if(optionalCliente.isPresent()){
            Cliente clienteExistente = optionalCliente.get();
            return GenericResponse.getResponse(400, "El cliente con documento " + request.getDocId() + " ya existe.", clienteExistente);
        }

        Cliente clienteNuevo = new Cliente();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear cliente", errors);

        clienteNuevo.setName(request.getName());
        clienteNuevo.setLastName(request.getLastName());
        clienteNuevo.setDocId(request.getDocId());
        clienteNuevo.setEmail(request.getEmail());
        clienteNuevo.setPhone(request.getPhone());
        clienteNuevo.setAddress(request.getAddress());
        clienteNuevo.setRefAddress(request.getRefAddress());

        try {
            clienteNuevo = clienteRepository.save(clienteNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear cliente: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Cliente creado", clienteNuevo);
    }

    @Override
    public GenericResponse<?> update(ClienteRequest request, Long id, BindingResult result){
        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al actualizar cliente", errors);

        Cliente clienteActual;
        Optional<Cliente> optionalClienteActual = clienteRepository.findById(id);
        Cliente clienteEditado = null;

        if(optionalClienteActual.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + id,
                            null);
        clienteActual = optionalClienteActual.get();

        try {
            clienteActual.setName(request.getName());
            clienteActual.setLastName(request.getLastName());
            clienteActual.setDocId(request.getDocId());
            clienteActual.setEmail(request.getEmail());
            clienteActual.setPhone(request.getPhone());
            clienteActual.setAddress(request.getAddress());
            clienteActual.setRefAddress(request.getRefAddress());
            clienteEditado = clienteRepository.save(clienteActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar cliente: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Cliente actualizado", clienteEditado);
    }

    @Override
    public GenericResponse<?> delete(Long id){
        try {
            clienteRepository.deleteById(id);
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al realizar la consulta en la base de datos: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
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
                            "Error al cargar clientes desde CSV, revisar archivo ",
                            null);
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al cargar clientes desde CSV: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
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
