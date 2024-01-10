package com.viamatica.svbackend.repository;

import com.viamatica.svbackend.model.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
