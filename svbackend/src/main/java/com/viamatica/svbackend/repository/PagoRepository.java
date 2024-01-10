package com.viamatica.svbackend.repository;

import com.viamatica.svbackend.model.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
