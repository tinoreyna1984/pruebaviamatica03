package com.viamatica.svbackend.service.crud.impl;

import com.viamatica.svbackend.model.dto.request.CajaRequest;
import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Caja;
import com.viamatica.svbackend.repository.CajaRepository;
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
public class CajaService implements GenericService<Caja, CajaRequest> {

    @Autowired
    private CajaRepository cajaRepository;
    private final HelperClass helperClass = new HelperClass();

    @Override
    public GenericResponse<?> get(Integer page, Integer size){
        try{
            if (page != null && size != null){
                Pageable pageable = PageRequest.of(page, size);
                Page<Caja> pageResult = cajaRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran las cajas", pageResult);
            }
            else {
                List<Caja> cajas = cajaRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran las cajas", cajas);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar cajas: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
    }

    @Override
    public GenericResponse<Caja> getById(Long id) {
        Caja caja;
        Optional<Caja> optionalCaja = cajaRepository.findById(id);
        try {
            if(optionalCaja.isEmpty()){
                return GenericResponse
                        .getResponse(400,
                                "No se encuentra la caja con ID " + id,
                                null);
            }
            caja = optionalCaja.get();
        }catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al buscar caja: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Caja encontrada", caja);
    }

    @Override
    public GenericResponse<?> save(CajaRequest request, BindingResult result) {
        Caja cajaNueva = new Caja();

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear caja", errors);

        cajaNueva.setDescripcion(request.getDescripcion());
        cajaNueva.setActive(true);

        try {
            cajaNueva = cajaRepository.save(cajaNueva);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al crear caja: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }

        return GenericResponse.getResponse(201, "Caja creada", cajaNueva);
    }

    @Override
    public GenericResponse<?> update(CajaRequest request, Long id, BindingResult result) {

        // proceso de validación
        String errors = helperClass.validaRequest(result);
        if (!errors.isEmpty())
            return GenericResponse.getResponse(400, "Error al crear caja", errors);

        Optional<Caja> optionalCaja = cajaRepository.findById(id);
        Caja cajaEditada;
        if(optionalCaja.isEmpty())
            return GenericResponse
                    .getResponse(400,
                            "No se encuentra la caja con ID " + id,
                            null);
        Caja cajaActual = optionalCaja.get();
        cajaActual.setDescripcion(request.getDescripcion());
        cajaActual.setActive(request.isActive());

        try {
            cajaEditada = cajaRepository.save(cajaActual);
        } catch(DataAccessException e) {
            return GenericResponse
                    .getResponse(500,
                            "Error al actualizar caja: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        } catch (Exception e){
            return GenericResponse
                    .getResponse(500,
                            "Error inesperado: " + e.getMessage(),
                            null);
        }
        return GenericResponse.getResponse(200, "Caja actualizada", cajaEditada);
    }

    @Override
    public GenericResponse<?> delete(Long id) {
        try {
            cajaRepository.deleteById(id);
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
        return GenericResponse.getResponse(200, "Caja borrada", null);
    }
}
