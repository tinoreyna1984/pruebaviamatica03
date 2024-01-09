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
}
