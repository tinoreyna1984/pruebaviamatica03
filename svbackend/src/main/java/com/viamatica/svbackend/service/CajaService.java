package com.viamatica.svbackend.service;

import com.viamatica.svbackend.model.dto.response.GenericResponse;
import com.viamatica.svbackend.model.entity.Caja;
import com.viamatica.svbackend.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CajaService {

    @Autowired
    private CajaRepository cajaRepository;

    public GenericResponse<?> getCajas(Integer page, Integer size){
        try{
            if (page != null && size != null){
                Pageable pageable = PageRequest.of(page, size);
                Page<Caja> pageResult = cajaRepository.findAll(pageable);
                return GenericResponse.getResponse(200, "Se encuentran los registros", pageResult);
            }
            else {
                List<Caja> cajas = cajaRepository.findAll();
                return GenericResponse.getResponse(200, "Se encuentran los registros", cajas);
            }
        } catch (DataAccessException e){
            return GenericResponse
                    .getResponse(500,
                            "Error al consultar cajas: " + e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()),
                            null);
        }
    }


}
