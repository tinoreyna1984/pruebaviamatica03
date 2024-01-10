package com.viamatica.svbackend.repository;

import com.viamatica.svbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userStatus = 'APPROVED', u.approvalDate = CURRENT_TIMESTAMP WHERE u.id = :id")
    void approveUser(@Param(value = "id") Long id);

    @Query(value = "SELECT approvedUsers()", nativeQuery = true)
    long approvedUsers();
    @Query(value = "SELECT operatorsCount()", nativeQuery = true)
    long operatorsCount();

    // asigna caja
    @Query(value = "select count(*) from usuario_caja where caja_id=:caja_id and usuario_id=:usuario_id", nativeQuery = true)
    long verificaUsuariosCajas(@Param(value = "usuario_id") Long usuario_id, @Param(value = "caja_id") Long caja_id);
    @Query(value = "select count(*) from usuario_caja where usuario_id=:usuario_id", nativeQuery = true)
    long limiteCajaPorUsuario(@Param(value = "usuario_id") Long usuario_id);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuario_caja VALUES(:caja_id, :usuario_id)", nativeQuery = true)
    void agregaUsuarioACaja(@Param(value = "usuario_id") Long usuario_id, @Param(value = "caja_id") Long caja_id);
}
