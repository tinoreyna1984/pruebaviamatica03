package com.viamatica.svbackend.repository;

import com.viamatica.svbackend.model.entity.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
}
