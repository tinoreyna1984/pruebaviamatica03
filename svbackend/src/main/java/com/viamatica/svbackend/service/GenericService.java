package com.viamatica.svbackend.service;

import com.viamatica.svbackend.model.dto.response.GenericResponse;
import org.springframework.validation.BindingResult;

public interface GenericService<T, R> {
    GenericResponse<?> get(Integer page, Integer size);
    GenericResponse<T> getById(Long id);
    GenericResponse<?> save(R request, BindingResult result);
    GenericResponse<?> update(R request, Long id, BindingResult result);
    GenericResponse<?> delete(Long id);
}
