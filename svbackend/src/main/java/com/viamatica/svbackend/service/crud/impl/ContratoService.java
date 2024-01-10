package com.viamatica.svbackend.service.crud.impl;

import com.viamatica.svbackend.model.dto.request.ContratoRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.model.entity.Contrato;
import com.viamatica.svbackend.model.entity.Servicio;
import com.viamatica.svbackend.repository.ClienteRepository;
import com.viamatica.svbackend.repository.ContratoRepository;
import com.viamatica.svbackend.repository.ServicioRepository;
import com.viamatica.svbackend.service.crud.GenericService;
import com.viamatica.svbackend.util.enums.ContractStatus;
import com.viamatica.svbackend.util.enums.PaymentMethod;
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
public class ContratoService implements GenericService<Contrato, ContratoRequest> {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size) {
        try{
            if (page != null && size != null) {
                // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
                Pageable pageable = PageRequest.of(page, size);
                Page<Contrato> pageResult = contratoRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los contratos", pageResult);
            } else {
                // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
                List<Contrato> users = contratoRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los contratos", users);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar contratos: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Contrato> getById(Long id) {
        Contrato contrato = null;
        try {
            contrato = contratoRepository.findById(id).get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar contrato: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Contrato encontrado", contrato);
    }

    @Override
    public GenericResponse<?> save(ContratoRequest request, BindingResult result) {
        Contrato contratoNuevo = new Contrato();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear contrato", errors);

        // busca cliente y servicio
        Cliente cliente = clienteRepository.findById(request.getClienteId()).get();
        Servicio servicio = servicioRepository.findById(request.getServicioId()).get();
        if(cliente == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + request.getClienteId(),
                            null);
        if(servicio == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el servicio con ID " + request.getServicioId(),
                            null);

        contratoNuevo.setFechaInicioContrato(request.getFechaInicioContrato());
        contratoNuevo.setFechaFinContrato(request.getFechaFinContrato());
        contratoNuevo.setContractStatus(ContractStatus.VIG);
        if(request.getPaymentMethod() == null)
            contratoNuevo.setPaymentMethod(PaymentMethod.TARJETA_DE_PAGO);
        else
            contratoNuevo.setPaymentMethod(request.getPaymentMethod());
        contratoNuevo.setCliente(cliente);
        contratoNuevo.setServicio(servicio);

        try {
            contratoNuevo = contratoRepository.save(contratoNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear contrato: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Contrato creado", contratoNuevo);
    }

    @Override
    public GenericResponse<?> update(ContratoRequest request, Long id, BindingResult result) {
        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al actualizar contrato", errors);

        Contrato contratoActual = contratoRepository.findById(id).get();
        Contrato contratoEditado = null;
        if(contratoActual == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el contrato con ID " + id,
                            null);

        // busca cliente y servicio
        Cliente cliente = clienteRepository.findById(request.getClienteId()).get();
        Servicio servicio = servicioRepository.findById(request.getServicioId()).get();
        if(cliente == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el cliente con ID " + request.getClienteId(),
                            null);
        if(servicio == null)
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el servicio con ID " + request.getServicioId(),
                            null);

        contratoActual.setFechaInicioContrato(request.getFechaInicioContrato());
        contratoActual.setFechaFinContrato(request.getFechaFinContrato());
        contratoActual.setContractStatus(request.getContractStatus());
        contratoActual.setPaymentMethod(request.getPaymentMethod());
        contratoActual.setCliente(cliente);
        contratoActual.setServicio(servicio);

        try {
            contratoEditado = contratoRepository.save(contratoActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar contrato: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Contrato actualizado", contratoEditado);
    }

    @Override
    public GenericResponse<?> delete(Long id) {
        try {
            contratoRepository.deleteById(id);
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

        return GenericResponse.getResponse(200, "Contrato borrado", null);
    }
}
