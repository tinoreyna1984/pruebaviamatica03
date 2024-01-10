package com.viamatica.svbackend.service.crud.impl;

import com.viamatica.svbackend.model.dto.request.AtencionRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Atencion;
import com.viamatica.svbackend.model.entity.Caja;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.repository.AtencionRepository;
import com.viamatica.svbackend.repository.CajaRepository;
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

import java.util.List;
import java.util.Optional;

@Service
public class AtencionService implements GenericService<Atencion, AtencionRequest> {

    @Autowired
    private AtencionRepository atencionRepository;
    @Autowired
    private CajaRepository cajaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size){
        try{
            if (page != null && size != null) {
                // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
                Pageable pageable = PageRequest.of(page, size);
                Page<Atencion> pageResult = atencionRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran las atenciones", pageResult);
            } else {
                // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
                List<Atencion> users = atencionRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran las atenciones", users);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar atenciones: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Atencion> getById(Long id){
        Atencion atencion;
        Optional<Atencion> optionalAtencion  = atencionRepository.findById(id);
        try {
            if(optionalAtencion.isEmpty())
                return GenericResponse
                        .getResponse(400,
                                "No se encuentra la atención con ID " + id,
                                null);
            atencion = optionalAtencion.get();
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar atención: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Atención encontrada", atencion);
    }

    @Override
    public GenericResponse<?> save(AtencionRequest request, BindingResult result){
        Atencion atencionNueva = new Atencion();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear atención", errors);

        // busca cliente y caja
        Optional<Cliente> optionalCliente = clienteRepository.findById(request.getClienteId());
        Optional<Caja> optionalCaja = cajaRepository.findById(request.getCajaId());
        if(optionalCliente.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + request.getClienteId(),
                            null);
        Cliente cliente = optionalCliente.get();
        if(optionalCaja.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la caja con ID " + request.getCajaId(),
                            null);
        Caja caja = optionalCaja.get();

        atencionNueva.setAttentionType(request.getAttentionType());
        atencionNueva.setAttentionStatus(request.getAttentionStatus());
        atencionNueva.setDescripcion(request.getAttentionType().getDescription());
        atencionNueva.setCliente(cliente);
        atencionNueva.setCaja(caja);

        try {
            atencionNueva = atencionRepository.save(atencionNueva);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear atención: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Atención creada", atencionNueva);
    }

    @Override
    public GenericResponse<?> update(AtencionRequest request, Long id, BindingResult result){
        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al actualizar atención", errors);

        Optional<Atencion> optionalAtencion = atencionRepository.findById(id);
        Atencion atencionEditada = null;
        if(optionalAtencion.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la atención con ID " + id,
                            null);
        Atencion atencionActual = optionalAtencion.get();

        // busca cliente y caja
        Optional<Cliente> optionalCliente = clienteRepository.findById(request.getClienteId());
        Optional<Caja> optionalCaja = cajaRepository.findById(request.getCajaId());
        if(optionalCliente.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + request.getClienteId(),
                            null);
        Cliente cliente = optionalCliente.get();
        if(optionalCaja.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la caja con ID " + request.getCajaId(),
                            null);
        Caja caja = optionalCaja.get();

        atencionActual.setAttentionType(request.getAttentionType());
        atencionActual.setAttentionStatus(request.getAttentionStatus());
        atencionActual.setDescripcion(request.getAttentionType().getDescription());
        atencionActual.setCliente(cliente);
        atencionActual.setCaja(caja);

        try {
            atencionEditada = atencionRepository.save(atencionActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar atención: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Atención actualizada", atencionEditada);
    }

    @Override
    public GenericResponse<?> delete(Long id){
        try {
            atencionRepository.deleteById(id);
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

        return GenericResponse.getResponse(200, "Atención borrada", null);
    }

}
