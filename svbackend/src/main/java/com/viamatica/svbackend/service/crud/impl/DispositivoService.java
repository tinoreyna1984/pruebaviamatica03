package com.viamatica.svbackend.service.crud.impl;

import com.viamatica.svbackend.model.dto.request.DispositivoRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Dispositivo;
import com.viamatica.svbackend.repository.DispositivoRepository;
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
public class DispositivoService implements GenericService<Dispositivo, DispositivoRequest> {
    @Autowired
    private DispositivoRepository dispositivoRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size) {
        try{
            if (page != null && size != null){
                Pageable pageable = PageRequest.of(page, size);
                Page<Dispositivo> pageResult = dispositivoRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los dispositivos", pageResult);
            }
            else {
                List<Dispositivo> dispositivos = dispositivoRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los dispositivos", dispositivos);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar dispositivos: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Dispositivo> getById(Long id) {
        Optional<Dispositivo> optionalDispositivo = dispositivoRepository.findById(id);
        Dispositivo dispositivo;
        try {
            if(optionalDispositivo.isEmpty()){
                return GenericResponse
                        .getResponse(400,
                                "No se encuentra el dispositivo con ID " + id,
                                null);
            }
            dispositivo = optionalDispositivo.get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar dispositivo: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Dispositivo encontrado", dispositivo);
    }

    @Override
    public GenericResponse<?> save(DispositivoRequest request, BindingResult result) {
        Dispositivo dispositivoNuevo = new Dispositivo();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear dispositivo", errors);

        dispositivoNuevo.setNombre(request.getNombre());

        try {
            dispositivoNuevo = dispositivoRepository.save(dispositivoNuevo);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear dispositivo: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Dispositivo creado", dispositivoNuevo);
    }

    @Override
    public GenericResponse<?> update(DispositivoRequest request, Long id, BindingResult result) {

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear dispositivo", errors);

        Optional<Dispositivo> optionalDispositivoActual = dispositivoRepository.findById(id);
        Dispositivo dispositivoEditado = null;
        if(optionalDispositivoActual.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra el dispositivo con ID " + id,
                            null);
        Dispositivo dispositivoActual = optionalDispositivoActual.get();

        dispositivoActual.setNombre(request.getNombre());

        try {
            dispositivoEditado = dispositivoRepository.save(dispositivoActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar dispositivo: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Dispositivo actualizado", dispositivoEditado);
    }

    @Override
    public GenericResponse<?> delete(Long id) {
        try {
            dispositivoRepository.deleteById(id);
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
        return GenericResponse.getResponse(200, "Dispositivo borrado", null);
    }
}
