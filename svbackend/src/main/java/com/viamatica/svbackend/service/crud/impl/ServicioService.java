package com.viamatica.svbackend.service.crud.impl;

import com.viamatica.svbackend.model.dto.request.ServicioRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Dispositivo;
import com.viamatica.svbackend.model.entity.Servicio;
import com.viamatica.svbackend.repository.DispositivoRepository;
import com.viamatica.svbackend.repository.ServicioRepository;
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
public class ServicioService implements GenericService<Servicio, ServicioRequest> {
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private DispositivoRepository dispositivoRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size) {
        try{
            if (page != null && size != null){
                Pageable pageable = PageRequest.of(page, size);
                Page<Servicio> pageResult = servicioRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los servicios", pageResult);
            }
            else {
                List<Servicio> servicios = servicioRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los servicios", servicios);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar servicios: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Servicio> getById(Long id) {
        Optional<Servicio> optionalServicio = servicioRepository.findById(id);
        Servicio servicio;
        try {
            if(optionalServicio.isEmpty()){
                return GenericResponse
                        .getResponse(400,
                                "No se encuentra el servicio con ID " + id,
                                null);
            }
            servicio = optionalServicio.get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar servicio: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Servicio encontrado", servicio);
    }

    @Override
    public GenericResponse<?> save(ServicioRequest request, BindingResult result) {
        Servicio servicioNuevo = new Servicio();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear dispositivo", errors);

        Optional<Dispositivo> optionalDispositivo = dispositivoRepository.findById(request.getDispositivoId());
        if(optionalDispositivo.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el dispositivo con ID " + request.getDispositivoId(),
                            null);
        Dispositivo dispositivo = optionalDispositivo.get();

        servicioNuevo.setDescripcion(request.getDescripcion());
        servicioNuevo.setPrecio(request.getPrecio());
        servicioNuevo.setDispositivo(dispositivo);

        try {
            servicioNuevo = servicioRepository.save(servicioNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear servicio: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Servicio creado", servicioNuevo);
    }

    @Override
    public GenericResponse<?> update(ServicioRequest request, Long id, BindingResult result) {

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear dispositivo", errors);

        Optional<Servicio> optionalServicioActual = servicioRepository.findById(id);
        Servicio servicioEditado;
        if(optionalServicioActual.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el servicio con ID " + id,
                            null);
        Servicio servicioActual = optionalServicioActual.get();

        Optional<Dispositivo> optionalDispositivo = dispositivoRepository.findById(request.getDispositivoId());
        if(optionalDispositivo.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el dispositivo con ID " + request.getDispositivoId(),
                            null);
        Dispositivo dispositivo = optionalDispositivo.get();

        servicioActual.setDescripcion(request.getDescripcion());
        servicioActual.setPrecio(request.getPrecio());
        servicioActual.setDispositivo(dispositivo);

        try {
            servicioEditado = servicioRepository.save(servicioActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar servicio: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Servicio actualizado", servicioEditado);
    }

    @Override
    public GenericResponse<?> delete(Long id) {
        try {
            servicioRepository.deleteById(id);
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
        return GenericResponse.getResponse(200, "Servicio borrado", null);
    }
}
