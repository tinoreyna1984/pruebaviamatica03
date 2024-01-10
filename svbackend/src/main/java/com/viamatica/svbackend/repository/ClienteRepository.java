package com.viamatica.svbackend.repository;

import com.viamatica.svbackend.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
