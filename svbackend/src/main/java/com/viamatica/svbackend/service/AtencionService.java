package com.viamatica.svbackend.service;

import com.viamatica.svbackend.model.dto.request.AtencionRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Atencion;
import com.viamatica.svbackend.model.entity.Caja;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.repository.AtencionRepository;
import com.viamatica.svbackend.repository.CajaRepository;
import com.viamatica.svbackend.repository.ClienteRepository;
import com.viamatica.svbackend.util.helpers.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;
    @Autowired
    private CajaRepository cajaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    private final HelperClass helperClass = new HelperClass();


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

    public GenericResponse<Atencion> getById(Long id){
        Atencion atencion = null;
        try {
            atencion = atencionRepository.findById(id).get();
        }catch(DataAccessException e) {
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


    public GenericResponse<?> save(AtencionRequest atencionRequest, BindingResult result){
        Atencion atencionNueva = new Atencion();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear usuario", errors);

        // busca cliente y caja
        Cliente cliente = new Cliente();
        Caja caja = new Caja();
        cliente = clienteRepository.findById(atencionRequest.getClienteId()).get();
        caja = cajaRepository.findById(atencionRequest.getCajaId()).get();
        if(cliente == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + atencionRequest.getClienteId(),
                            null);
        if(caja == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la caja con ID " + atencionRequest.getCajaId(),
                            null);

        atencionNueva.setAttentionType(atencionRequest.getAttentionType());
        atencionNueva.setAttentionStatus(atencionRequest.getAttentionStatus());
        atencionNueva.setDescripcion(atencionRequest.getAttentionType().getDescription());
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


    public GenericResponse<?> update(AtencionRequest atencionRequest, Long id, BindingResult result){
        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al actualizar usuario", errors);

        Atencion atencionActual = atencionRepository.findById(id).get();
        Atencion atencionEditada = null;
        if(atencionActual == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la atención con ID " + id,
                            null);

        // busca cliente y caja
        Cliente cliente = new Cliente();
        Caja caja = new Caja();
        cliente = clienteRepository.findById(atencionRequest.getClienteId()).get();
        caja = cajaRepository.findById(atencionRequest.getCajaId()).get();
        if(cliente == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + atencionRequest.getClienteId(),
                            null);
        if(caja == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la caja con ID " + atencionRequest.getCajaId(),
                            null);

        atencionActual.setAttentionType(atencionRequest.getAttentionType());
        atencionActual.setAttentionStatus(atencionRequest.getAttentionStatus());
        atencionActual.setDescripcion(atencionRequest.getAttentionType().getDescription());

        try {
            atencionEditada = atencionRepository.save(atencionActual);
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
        return GenericResponse.getResponse(200, "Cliente actualizado", atencionEditada);
    }

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
