package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
    boolean existsByEmail(final String email);
    @Transactional
    @Modifying
    @Query("UPDATE User "+
        "SET emailVerification = :emailVerification " +
        "WHERE id = :id")
    void updateEmailVerificationById(Long id, boolean emailVerification);
}
