package com.viamatica.svbackend.service.crud.impl;

import com.viamatica.svbackend.model.dto.request.PagoRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.model.entity.Pago;
import com.viamatica.svbackend.repository.ClienteRepository;
import com.viamatica.svbackend.repository.PagoRepository;
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
@SuppressWarnings({"null"})
public class PagoService implements GenericService<Pago, PagoRequest> {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size) {
        try{
            if (page != null && size != null) {
                // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
                Pageable pageable = PageRequest.of(page, size);
                Page<Pago> pageResult = pagoRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los pagos", pageResult);
            } else {
                // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
                List<Pago> users = pagoRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los pagos", users);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar pagos: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Pago> getById(Long id) {
        Pago pago;
        Optional<Pago> optionalPago = pagoRepository.findById(id);
        try {
            if(optionalPago.isEmpty()){
                return GenericResponse
                        .getResponse(400,
                                "No se encuentra el pago con ID " + id,
                                null);
            }
            pago = optionalPago.get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar pago: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Pago encontrado", pago);
    }

    @Override
    public GenericResponse<?> save(PagoRequest request, BindingResult result) {
        Pago pagoNuevo = new Pago();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear pago", errors);

        // busca cliente
        Optional<Cliente> optionalCliente = clienteRepository.findById(request.getClienteId());
        if(optionalCliente.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + request.getClienteId(),
                            null);
        Cliente cliente = optionalCliente.get();

        pagoNuevo.setFechaPago(request.getFechaPago());
        pagoNuevo.setCliente(cliente);

        try {
            pagoNuevo = pagoRepository.save(pagoNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear pago: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Pago creado", pagoNuevo);
    }

    @Override
    public GenericResponse<?> update(PagoRequest request, Long id, BindingResult result) {

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear pago", errors);

        Optional<Pago> optionalPagoActual = pagoRepository.findById(id);
        Pago pagoEditado;
        if(optionalPagoActual.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el pago con ID " + id,
                            null);
        Pago pagoActual = optionalPagoActual.get();

        // busca cliente
        Optional<Cliente> optionalCliente = clienteRepository.findById(request.getClienteId());
        if(optionalCliente.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + request.getClienteId(),
                            null);
        Cliente cliente = optionalCliente.get();

        pagoActual.setFechaPago(request.getFechaPago());
        pagoActual.setCliente(cliente);

        try {
            pagoEditado = pagoRepository.save(pagoActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar pago: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Pago actualizado", pagoEditado);
    }

    @Override
    public GenericResponse<?> delete(Long id) {
        try {
            pagoRepository.deleteById(id);
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

        return GenericResponse.getResponse(200, "Pago borrado", null);
    }
}
