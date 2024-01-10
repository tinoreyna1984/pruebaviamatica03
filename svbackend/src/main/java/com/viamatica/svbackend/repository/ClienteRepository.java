package com.viamatica.svbackend.repository;

import com.viamatica.svbackend.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDocId(String docId);
}
